package com.testcar.car.domains.track.request;

import static com.testcar.car.common.Constant.TOMORROW;
import static com.testcar.car.common.Constant.TRACK_DESCRIPTION;
import static com.testcar.car.common.Constant.TRACK_LENGTH;
import static com.testcar.car.common.Constant.TRACK_LOCATION;
import static com.testcar.car.common.Constant.TRACK_NAME;
import static com.testcar.car.common.Constant.TRACK_RESERVATION_DATE;
import static com.testcar.car.common.Constant.YESTERDAY;

import com.testcar.car.domains.track.model.DeleteTrackRequest;
import com.testcar.car.domains.track.model.RegisterTrackRequest;
import com.testcar.car.domains.trackReservation.model.TrackReservationRequest;
import com.testcar.car.domains.trackReservation.model.vo.ReservationSlotVo;
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
        return List.of(createReservationSlotVo(11), createReservationSlotVo(12));
    }

    public static TrackReservationRequest createTrackReservationRequest() {
        return TrackReservationRequest.builder()
                .date(TRACK_RESERVATION_DATE)
                .reservationSlots(createReservationSlotVoList())
                .build();
    }

    public static TrackReservationRequest createInvalidTrackReservationRequest() {
        return TrackReservationRequest.builder()
                .date(TRACK_RESERVATION_DATE)
                .reservationSlots(
                        List.of(
                                ReservationSlotVo.builder()
                                        .startedAt(YESTERDAY.withHour(12))
                                        .expiredAt(YESTERDAY.withHour(13))
                                        .build()))
                .build();
    }

    private static ReservationSlotVo createReservationSlotVo(int hour) {
        return ReservationSlotVo.builder()
                .startedAt(TOMORROW.withHour(hour))
                .expiredAt(TOMORROW.withHour(hour + 1))
                .build();
    }
}
