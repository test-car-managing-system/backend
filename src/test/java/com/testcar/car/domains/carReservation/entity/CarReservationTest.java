package com.testcar.car.domains.carReservation.entity;

import static org.assertj.core.api.Assertions.assertThat;

import com.testcar.car.domains.car.entity.Car;
import com.testcar.car.domains.car.entity.Type;
import com.testcar.car.domains.carStock.entity.CarStock;
import com.testcar.car.domains.carStock.entity.StockStatus;
import com.testcar.car.domains.member.Member;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CarReservationTest {
    private Member member;
    private CarStock carStock;

    @BeforeEach
    public void setUp() {
        member = Member.builder().name("홍길동").build();
        final Car car = Car.builder().name("아반떼").displacement(1.6).type(Type.SEDAN).build();
        carStock =
                CarStock.builder()
                        .car(car)
                        .stockNumber("123456789012")
                        .status(StockStatus.AVAILABLE)
                        .build();
    }

    @Test
    public void createCarReservationTest() {
        // Given
        final LocalDateTime startedAt = LocalDateTime.of(2021, 1, 1, 0, 0, 0);
        final LocalDateTime expiredAt = LocalDateTime.of(2021, 1, 8, 0, 0, 0);

        // When
        final CarReservation carReservation =
                CarReservation.builder()
                        .member(member)
                        .carStock(carStock)
                        .startedAt(startedAt)
                        .expiredAt(expiredAt)
                        .status(ReservationStatus.RESERVED)
                        .build();
        // Then
        assertThat(carReservation.getMember()).isEqualTo(member);
        assertThat(carReservation.getCarStock()).isEqualTo(carStock);
        assertThat(carReservation.getStartedAt()).isEqualTo(startedAt);
        assertThat(carReservation.getExpiredAt()).isEqualTo(expiredAt);
        assertThat(carReservation.getStatus()).isEqualTo(ReservationStatus.RESERVED);
    }

    @Test
    public void carReservationReturnTest() {
        // Given
        final LocalDateTime startedAt = LocalDateTime.of(2021, 1, 1, 0, 0, 0);
        final LocalDateTime expiredAt = LocalDateTime.of(2021, 1, 8, 0, 0, 0);
        final CarReservation carReservation =
                CarReservation.builder()
                        .member(member)
                        .carStock(carStock)
                        .startedAt(startedAt)
                        .expiredAt(expiredAt)
                        .status(ReservationStatus.RESERVED)
                        .build();

        // When
        carReservation.updateReturn();

        // Then
        assertThat(carReservation.getStatus()).isEqualTo(ReservationStatus.RETURNED);
    }
}
