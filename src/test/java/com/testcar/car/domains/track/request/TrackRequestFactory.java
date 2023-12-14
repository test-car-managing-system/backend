package com.testcar.car.domains.track.request;

import static com.testcar.car.common.Constant.ANOTHER_TRACK_RESERVATION_SLOT_EXPIRED_AT;
import static com.testcar.car.common.Constant.ANOTHER_TRACK_RESERVATION_SLOT_STARTED_AT;
import static com.testcar.car.common.Constant.TRACK_DESCRIPTION;
import static com.testcar.car.common.Constant.TRACK_LENGTH;
import static com.testcar.car.common.Constant.TRACK_LOCATION;
import static com.testcar.car.common.Constant.TRACK_NAME;
import static com.testcar.car.common.Constant.TRACK_RESERVATION_DATE;
import static com.testcar.car.common.Constant.TRACK_RESERVATION_SLOT_EXPIRED_AT;
import static com.testcar.car.common.Constant.TRACK_RESERVATION_SLOT_STARTED_AT;

import com.testcar.car.domains.track.model.DeleteTrackRequest;
import com.testcar.car.domains.track.model.RegisterTrackRequest;
import com.testcar.car.domains.trackReservation.model.TrackReservationRequest;
import com.testcar.car.domains.trackReservation.model.vo.ReservationSlotVo;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TrackRequestFactory {
    private TrackRequestFactory() {}

    public static RegisterTrackRequest createRegisterTrackRequest() {
        return RegisterTrackRequest.builder()
                .name(TRACK_NAME)
                .location(TRACK_LOCATION)
                .description(TRACK_DESCRIPTION)
                .length(TRACK_LENGTH)
                .build();
    }

    public static RegisterTrackRequest createRegisterTrackRequest(String name) {
        return RegisterTrackRequest.builder()
                .name(name)
                .location(TRACK_LOCATION)
                .description(TRACK_DESCRIPTION)
                .length(TRACK_LENGTH)
                .build();
    }

    public static DeleteTrackRequest createDeleteTrackRequest(List<Long> ids) {
        return DeleteTrackRequest.builder().ids(ids).build();
    }

    private static List<ReservationSlotVo> createReservationSlotVoList() {
        return List.of(
                createReservationSlotVo(
                        TRACK_RESERVATION_SLOT_STARTED_AT, TRACK_RESERVATION_SLOT_EXPIRED_AT));
    }

    private static List<ReservationSlotVo> createAnotherReservationSlotVoList() {
        return List.of(
                createReservationSlotVo(
                        ANOTHER_TRACK_RESERVATION_SLOT_STARTED_AT,
                        ANOTHER_TRACK_RESERVATION_SLOT_EXPIRED_AT));
    }

    public static TrackReservationRequest createTrackReservationRequest() {
        return TrackReservationRequest.builder()
                .date(TRACK_RESERVATION_DATE)
                .reservationSlots(createReservationSlotVoList())
                .build();
    }

    public static TrackReservationRequest createAnotherTrackReservationRequest() {
        return TrackReservationRequest.builder()
                .date(TRACK_RESERVATION_DATE)
                .reservationSlots(createAnotherReservationSlotVoList())
                .build();
    }

    public static TrackReservationRequest createTrackReservationRequest(
            LocalDate date, List<ReservationSlotVo> slots) {
        return TrackReservationRequest.builder().date(date).reservationSlots(slots).build();
    }

    public static ReservationSlotVo createReservationSlotVo(
            LocalDate date, int startHour, int endHour) {
        return ReservationSlotVo.builder()
                .startedAt(date.atStartOfDay().withHour(startHour))
                .expiredAt(date.atStartOfDay().withHour(endHour))
                .build();
    }

    public static ReservationSlotVo createReservationSlotVo(
            LocalDateTime start, LocalDateTime end) {
        return ReservationSlotVo.builder().startedAt(start).expiredAt(end).build();
    }
}
