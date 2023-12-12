package com.testcar.car.common;

import static com.testcar.car.common.Constant.TRACK_DESCRIPTION;
import static com.testcar.car.common.Constant.TRACK_LENGTH;
import static com.testcar.car.common.Constant.TRACK_LOCATION;
import static com.testcar.car.common.Constant.TRACK_NAME;

import com.testcar.car.domains.track.Track;
import com.testcar.car.domains.track.Track.TrackBuilder;

public class TrackEntityFactory {
    private TrackEntityFactory() {}

    public static Track createTrack() {
        return createTrackBuilder().build();
    }

    public static TrackBuilder createTrackBuilder() {
        return Track.builder()
                .name(TRACK_NAME)
                .location(TRACK_LOCATION)
                .description(TRACK_DESCRIPTION)
                .length(TRACK_LENGTH);
    }
}
