package com.testcar.car.common;

import static com.testcar.car.common.Constant.TRACK_DESCRIPTION;
import static com.testcar.car.common.Constant.TRACK_LENGTH;
import static com.testcar.car.common.Constant.TRACK_LOCATION;
import static com.testcar.car.common.Constant.TRACK_NAME;
import static com.testcar.car.common.Constant.TRACK_RESERVATION_SLOT_EXPIRED_AT;
import static com.testcar.car.common.Constant.TRACK_RESERVATION_SLOT_STARTED_AT;

import com.testcar.car.domains.track.entity.Track;
import com.testcar.car.domains.track.entity.Track.TrackBuilder;
import com.testcar.car.domains.trackReservation.entity.ReservationStatus;
import com.testcar.car.domains.trackReservation.entity.TrackReservation;
import com.testcar.car.domains.trackReservation.entity.TrackReservation.TrackReservationBuilder;
import com.testcar.car.domains.trackReservation.entity.TrackReservationSlot;
import java.util.Set;

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

    public static TrackReservationSlot createTrackReservationSlot() {
        return createTrackReservationSlotBuilder().build();
    }

    public static TrackReservationSlot createTrackReservationSlot(
            TrackReservation trackReservation) {
        return createTrackReservationSlotBuilder()
                .trackReservation(trackReservation)
                .startedAt(TRACK_RESERVATION_SLOT_STARTED_AT)
                .expiredAt(TRACK_RESERVATION_SLOT_EXPIRED_AT)
                .build();
    }

    public static TrackReservationSlot.TrackReservationSlotBuilder
            createTrackReservationSlotBuilder() {
        return TrackReservationSlot.builder().trackReservation(createTrackReservation());
    }

    public static Set<TrackReservationSlot> createTrackReservationSlotSet() {
        final TrackReservation trackReservation = createTrackReservation();
        return Set.of(createTrackReservationSlot(trackReservation));
    }
}
