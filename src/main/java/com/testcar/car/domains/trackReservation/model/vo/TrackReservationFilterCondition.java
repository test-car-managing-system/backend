package com.testcar.car.domains.trackReservation.model.vo;


import com.testcar.car.common.annotation.DateFormat;
import com.testcar.car.domains.trackReservation.entity.ReservationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackReservationFilterCondition {
    @Schema(description = "시험장명", example = "null")
    private String name;

    @DateFormat
    @Schema(description = "예약일자", example = "null")
    private LocalDate createdAt;

    @Schema(description = "예약상태", example = "null", implementation = ReservationStatus.class)
    private ReservationStatus status;
}
