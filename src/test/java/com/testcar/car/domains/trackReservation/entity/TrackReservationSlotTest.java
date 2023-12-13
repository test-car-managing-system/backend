package com.testcar.car.domains.trackReservation.entity;

import static org.assertj.core.api.Assertions.assertThat;

import com.testcar.car.common.TrackEntityFactory;
import com.testcar.car.domains.track.Track;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class TrackReservationSlotTest {
    @Test
    public void 시험장_예약시간_슬롯_엔티티를_생성한다() {
        // given
        final Track track = TrackEntityFactory.createTrack();
        final TrackReservation trackReservation = TrackEntityFactory.createTrackReservation();
        final LocalDateTime startedAt = LocalDateTime.now();
        final LocalDateTime expiredAt = startedAt.plusHours(1L);

        // when
        final TrackReservationSlot trackReservationSlot =
                TrackReservationSlot.builder()
                        .track(track)
                        .trackReservation(trackReservation)
                        .startedAt(startedAt)
                        .expiredAt(expiredAt)
                        .build();

        // then
        assertThat(trackReservationSlot.getTrack()).isEqualTo(track);
        assertThat(trackReservationSlot.getTrackReservation()).isEqualTo(trackReservation);
        assertThat(trackReservationSlot.getStartedAt()).isEqualTo(startedAt);
        assertThat(trackReservationSlot.getExpiredAt()).isEqualTo(expiredAt);
    }
}
