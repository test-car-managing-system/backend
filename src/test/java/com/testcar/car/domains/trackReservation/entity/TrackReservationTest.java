package com.testcar.car.domains.trackReservation.entity;

import static org.assertj.core.api.Assertions.assertThat;

import com.testcar.car.common.MemberEntityFactory;
import com.testcar.car.common.TrackEntityFactory;
import com.testcar.car.domains.member.entity.Member;
import com.testcar.car.domains.track.entity.Track;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class TrackReservationTest {
    @Test
    public void 시험장_예약정보_엔티티를_생성한다() {
        // given
        final Member member = MemberEntityFactory.createMember();
        final Track track = TrackEntityFactory.createTrack();
        final ReservationStatus status = ReservationStatus.RESERVED;

        // when
        final TrackReservation trackReservation =
                TrackReservation.builder().member(member).track(track).status(status).build();

        // then
        assertThat(trackReservation.getMember()).isEqualTo(member);
        assertThat(trackReservation.getTrack()).isEqualTo(track);
        assertThat(trackReservation.getStatus()).isEqualTo(status);
    }

    @Test
    public void 시험장_예약을_취소한다() {
        // given
        final TrackReservation trackReservation = TrackEntityFactory.createTrackReservation();

        // when
        trackReservation.cancel();

        // then
        assertThat(trackReservation.getStatus()).isEqualTo(ReservationStatus.CANCELED);
    }

    @ParameterizedTest
    @EnumSource(
            value = ReservationStatus.class,
            names = {"RESERVED"})
    public void 시험장을_취소할수_있는지_확인한다(ReservationStatus status) {
        // given
        final TrackReservation trackReservation =
                TrackEntityFactory.createTrackReservationBuilder().status(status).build();

        // when
        final boolean isCancelable = trackReservation.isCancelable();

        // then
        assertThat(isCancelable).isTrue();
    }

    @ParameterizedTest
    @EnumSource(
            value = ReservationStatus.class,
            names = {"CANCELED", "COMPLETED"})
    public void 시험장을_취소할수_없는지_확인한다(ReservationStatus status) {
        // given
        final TrackReservation trackReservation =
                TrackEntityFactory.createTrackReservationBuilder().status(status).build();

        // when
        final boolean isCancelable = trackReservation.isCancelable();

        // then
        assertThat(isCancelable).isFalse();
    }
}
