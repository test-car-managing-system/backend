package com.testcar.car.domains.trackReservation.model;


import com.testcar.car.common.annotation.DateFormat;
import com.testcar.car.domains.trackReservation.model.vo.ReservationSlotVo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrackReservationRequest {
    @DateFormat
    @NotNull(message = "예약일자를 입력해주세요.")
    @Schema(description = "예약일자", example = "2021-10-10")
    private LocalDate date;

    @NotNull(message = "예약 시간 리스트를 입력해주세요.")
    @NotEmpty(message = "예약 시간 리스트를 입력해주세요.")
    @Schema(description = "예약 시간")
    private List<@Valid ReservationSlotVo> reservationSlots;
}
