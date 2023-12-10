package com.testcar.car.domains.trackReservation.model;


import com.testcar.car.common.annotation.DateFormat;
import com.testcar.car.domains.trackReservation.entity.TrackReservationSlot;
import com.testcar.car.domains.trackReservation.model.vo.ReservationSlotVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TrackReservationSlotResponse {
    @DateFormat
    @Schema(description = "날짜", example = "2023-01-01")
    private LocalDate date;

    @Schema(description = "예약된 시간")
    private List<ReservationSlotVo> reservationTime;

    public static TrackReservationSlotResponse of(LocalDate date, Set<TrackReservationSlot> slots) {
        return TrackReservationSlotResponse.builder()
                .date(date)
                .reservationTime(slots.stream().map(ReservationSlotVo::from).toList())
                .build();
    }
}
