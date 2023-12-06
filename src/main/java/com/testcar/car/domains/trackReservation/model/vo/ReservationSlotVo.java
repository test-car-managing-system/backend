package com.testcar.car.domains.trackReservation.model.vo;


import com.testcar.car.common.annotation.DateTimeFormat;
import com.testcar.car.domains.trackReservation.entity.TrackReservationSlot;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationSlotVo {

    @DateTimeFormat
    @Schema(description = "예약 시간", example = "2021-10-10 10:00:00")
    private LocalDateTime startedAt;

    @DateTimeFormat
    @Schema(description = "예약 시간", example = "2021-10-10 11:00:00")
    private LocalDateTime expiredAt;

    public static ReservationSlotVo from(TrackReservationSlot slot) {
        return ReservationSlotVo.builder()
                .startedAt(slot.getStartedAt())
                .expiredAt(slot.getExpiredAt())
                .build();
    }
}
