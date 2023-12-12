package com.testcar.car.domains.carTest.entity;

import static org.assertj.core.api.Assertions.assertThat;

import com.testcar.car.domains.car.entity.Car;
import com.testcar.car.domains.car.entity.Type;
import com.testcar.car.domains.carStock.entity.CarStock;
import com.testcar.car.domains.carStock.entity.StockStatus;
import com.testcar.car.domains.member.Member;
import com.testcar.car.domains.track.Track;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CarTestTest {
    private Member member;
    private CarStock carStock;
    private Track track;
    private CarTest carTest;

    @BeforeEach
    public void setUp() {
        final LocalDateTime performedAt = LocalDateTime.of(2021, 1, 1, 0, 0, 0);
        member = Member.builder().name("홍길동").build();
        final Car car = Car.builder().name("아반떼").displacement(1.6).type(Type.SEDAN).build();
        carStock =
                CarStock.builder()
                        .car(car)
                        .stockNumber("123456789012")
                        .status(StockStatus.AVAILABLE)
                        .build();
        track = Track.builder().name("시험장").build();
        carTest =
                CarTest.builder()
                        .member(member)
                        .carStock(carStock)
                        .track(track)
                        .performedAt(performedAt)
                        .result("통과")
                        .build();
    }

    @Test
    public void createCarTestTest() {
        // Given
        final LocalDateTime performedAt = LocalDateTime.now();

        // When
        final CarTest newCarTest =
                CarTest.builder()
                        .member(member)
                        .carStock(carStock)
                        .track(track)
                        .performedAt(performedAt)
                        .result("통과")
                        .build();
        // Then
        assertThat(newCarTest.getMember()).isEqualTo(member);
        assertThat(newCarTest.getCarStock()).isEqualTo(carStock);
        assertThat(newCarTest.getTrack()).isEqualTo(track);
        assertThat(newCarTest.getPerformedAt()).isEqualTo(performedAt);
        assertThat(newCarTest.getResult()).isEqualTo("통과");
    }

    @Test
    public void carTestUpdateTest() {
        // Given
        final LocalDateTime newPerformedAt = LocalDateTime.of(2021, 1, 2, 0, 0, 0);
        final String newStockNumber = "123456789013";
        final String newResult = "불합격";

        // When
        carTest.update(newPerformedAt, newStockNumber, newResult);

        // Then
        assertThat(carTest.getPerformedAt()).isEqualTo(newPerformedAt);
        assertThat(carTest.getResult()).isEqualTo(newStockNumber);
        assertThat(carTest.getMemo()).isEqualTo(newResult);
    }

    @Test
    public void carTestUpdateTrackTest() {
        // Given
        final Track newTrack = Track.builder().name("시험장2").build();

        // When
        carTest.updateTrack(newTrack);

        // Then
        assertThat(carTest.getTrack()).isEqualTo(newTrack);
    }

    @Test
    public void carTestUpdateStockTest() {
        // Given
        final CarStock newCarStock =
                CarStock.builder()
                        .car(carStock.getCar())
                        .stockNumber("123456789013")
                        .status(StockStatus.AVAILABLE)
                        .build();

        // When
        carTest.updateCarStock(newCarStock);

        // Then
        assertThat(carTest.getCarStock()).isEqualTo(newCarStock);
    }

    @Test
    public void carTestUpdateMemberTest() {
        // Given
        final Member newMember = Member.builder().name("홍길순").build();

        // When
        carTest.updateMemberBy(newMember);

        // Then
        assertThat(carTest.getUpdateMember()).isEqualTo(newMember);
    }
}
