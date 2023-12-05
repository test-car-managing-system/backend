package com.testcar.car.domains.trackReservation.model;


import com.testcar.car.common.annotation.DateFormat;
import com.testcar.car.domains.trackReservation.model.vo.ReservationSlotVo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;

@Getter
public class TrackReservationRequest {
    @NotNull
    @DateFormat
    @Schema(description = "예약일자", example = "2021-10-10")
    private LocalDate date;

    @NotNull
    @NotEmpty
    @Schema(description = "예약 시간")
    private List<ReservationSlotVo> reservationSlots;
}
