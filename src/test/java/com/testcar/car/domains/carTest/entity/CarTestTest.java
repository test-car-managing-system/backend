package com.testcar.car.domains.carTest.entity;

import static org.assertj.core.api.Assertions.assertThat;

import com.testcar.car.common.CarEntityFactory;
import com.testcar.car.common.MemberEntityFactory;
import com.testcar.car.common.TrackEntityFactory;
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
        member = MemberEntityFactory.createMember();
        carStock = CarEntityFactory.createCarStock();
        track = TrackEntityFactory.createTrack();
        carTest = CarEntityFactory.createCarTest();
    }

    @Test
    public void 시험_수행이력을_생성한다() {
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
    public void 시험_수행이력의_정보를_변경한다() {
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
    public void 시험_수행이력의_주행시험장을_변경한다() {
        // Given
        final Track newTrack = Track.builder().name("마포주행시험장").build();

        // When
        carTest.updateTrack(newTrack);

        // Then
        assertThat(carTest.getTrack()).isEqualTo(newTrack);
    }

    @Test
    public void 시험_수행이력의_차량재고를_변경한다() {
        // Given
        final CarStock newCarStock = CarEntityFactory.createCarStock();

        // When
        carTest.updateCarStock(newCarStock);

        // Then
        assertThat(carTest.getCarStock()).isEqualTo(newCarStock);
    }

    @Test
    public void 시험_수행이력의_수정인을_변경한다() {
        // Given
        final Member newMember = Member.builder().name("홍길순").build();

        // When
        carTest.updateMemberBy(newMember);

        // Then
        assertThat(carTest.getUpdateMember()).isEqualTo(newMember);
    }
}
