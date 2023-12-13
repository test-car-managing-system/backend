package com.testcar.car.common;

import static com.testcar.car.common.Constant.TRACK_DESCRIPTION;
import static com.testcar.car.common.Constant.TRACK_LENGTH;
import static com.testcar.car.common.Constant.TRACK_LOCATION;
import static com.testcar.car.common.Constant.TRACK_NAME;

import com.testcar.car.domains.track.Track;
import com.testcar.car.domains.track.Track.TrackBuilder;
import com.testcar.car.domains.trackReservation.entity.ReservationStatus;
import com.testcar.car.domains.trackReservation.entity.TrackReservation;
import com.testcar.car.domains.trackReservation.entity.TrackReservation.TrackReservationBuilder;

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

    public static TrackReservation createTrackReservation() {
        return createTrackReservationBuilder().build();
    }

    public static TrackReservationBuilder createTrackReservationBuilder() {
        return TrackReservation.builder()
                .member(MemberEntityFactory.createMember())
                .track(createTrack())
                .status(ReservationStatus.RESERVED);
    }
}
