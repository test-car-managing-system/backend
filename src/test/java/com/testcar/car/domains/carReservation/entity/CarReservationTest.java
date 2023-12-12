package com.testcar.car.domains.carReservation.entity;

import static org.assertj.core.api.Assertions.assertThat;

import com.testcar.car.common.CarEntityFactory;
import com.testcar.car.common.MemberEntityFactory;
import com.testcar.car.domains.carStock.entity.CarStock;
import com.testcar.car.domains.member.Member;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CarReservationTest {
    private static CarStock carStock;
    private static Member member;

    @BeforeAll
    public static void setUp() {
        member = MemberEntityFactory.createMember();
        carStock = CarEntityFactory.createCarStock();
    }

    @Test
    public void 차량_예약을_생성한다() {
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
    public void 예약했던_차량을_반납한다() {
        // Given
        final CarReservation carReservation = CarEntityFactory.createCarReservation();

        // When
        carReservation.updateReturn();

        // Then
        assertThat(carReservation.getStatus()).isEqualTo(ReservationStatus.RETURNED);
    }
}
