package com.testcar.car.common.database;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Supplier;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/** MySQL 의 NamedLock 관련 메소드 클래스 */
@Slf4j
@Component
@RequiredArgsConstructor
public class NamedLockManager implements LockManager {
    private static final String GET_LOCK = "SELECT GET_LOCK(?, ?)";
    private static final String RELEASE_LOCK = "SELECT RELEASE_LOCK(?)";
    private static final String NULL_EXCEPTION = "잘못된 인자입니다.";
    private static final String EMPTY_EXCEPTION = "잘못된 키값입니다.";
    private static final int TIMEOUT_SECONDS = 10;
    private final DataSource dataSource;

    @Transactional(propagation = Propagation.NEVER)
    public <T> T lock(String userLockName, Supplier<T> supplier) {
        try (Connection connection = dataSource.getConnection()) {
            try {
                getLock(connection, userLockName);
                return supplier.get();
            } finally {
                releaseLock(connection, userLockName);
            }
        } catch (SQLException | RuntimeException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private void getLock(Connection connection, String userLockName) {

        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_LOCK)) {
            preparedStatement.setString(1, userLockName);
            preparedStatement.setInt(2, TIMEOUT_SECONDS);

            validateLockStatement(preparedStatement);
        } catch (SQLException e) {
            log.error("releaseLock Error", e);
            throw new InvalidLockException(e.getMessage(), e);
        }
    }

    private void releaseLock(Connection connection, String userLockName) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(RELEASE_LOCK)) {
            preparedStatement.setString(1, userLockName);

            validateLockStatement(preparedStatement);
        } catch (SQLException e) {
            log.error("releaseLock Error", e);
            throw new InvalidLockException(e.getMessage(), e);
        }
    }

    private void validateLockStatement(PreparedStatement preparedStatement) throws SQLException {
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (!resultSet.next()) {
                throw new InvalidLockException(NULL_EXCEPTION);
            }
            int result = resultSet.getInt(1);
            if (result != 1) {
                throw new InvalidLockException(EMPTY_EXCEPTION);
            }
        }
    }
}
