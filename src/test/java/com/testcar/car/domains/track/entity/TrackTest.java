package com.testcar.car.domains.track.entity;

import static com.testcar.car.common.Constant.TRACK_DESCRIPTION;
import static com.testcar.car.common.Constant.TRACK_LENGTH;
import static com.testcar.car.common.Constant.TRACK_LOCATION;
import static com.testcar.car.common.Constant.TRACK_NAME;
import static org.assertj.core.api.Assertions.assertThat;

import com.testcar.car.common.TrackEntityFactory;
import org.junit.jupiter.api.Test;

public class TrackTest {
    @Test
    public void 시험장_엔티티를_생성한다() {
        // given
        final String name = TRACK_NAME;
        final String location = TRACK_LOCATION;
        final String description = TRACK_DESCRIPTION;
        final Double length = TRACK_LENGTH;

        // when
        final Track track =
                Track.builder()
                        .name(name)
                        .location(location)
                        .description(description)
                        .length(length)
                        .build();

        // then
        assertThat(track.getName()).isEqualTo(name);
        assertThat(track.getLocation()).isEqualTo(location);
        assertThat(track.getDescription()).isEqualTo(description);
        assertThat(track.getLength()).isEqualTo(length);
    }

    @Test
    public void 시험장_정보를_변경한다() {
        // given
        final Track track = TrackEntityFactory.createTrack();
        final Track updateTrack =
                Track.builder()
                        .name("급선회로")
                        .location("서울주행시험장")
                        .description("급경사")
                        .length(700.0)
                        .build();

        // when
        track.update(updateTrack);

        // then
        assertThat(track.getName()).isEqualTo(updateTrack.getName());
        assertThat(track.getLocation()).isEqualTo(updateTrack.getLocation());
        assertThat(track.getDescription()).isEqualTo(updateTrack.getDescription());
        assertThat(track.getLength()).isEqualTo(updateTrack.getLength());
    }
}
