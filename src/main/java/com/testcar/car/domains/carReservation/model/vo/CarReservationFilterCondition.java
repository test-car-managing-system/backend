package com.testcar.car.domains.carReservation.model.vo;


import com.testcar.car.common.annotation.DateFormat;
import com.testcar.car.domains.carReservation.entity.ReservationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarReservationFilterCondition {
    @Schema(description = "차량명", example = "null")
    private String name;

    @Schema(description = "대여 상태", example = "null", implementation = ReservationStatus.class)
    private ReservationStatus status;

    @DateFormat
    @Schema(description = "대여일자 시작일", example = "null")
    private LocalDate startDate;

    @DateFormat
    @Schema(description = "대여일자 종료일", example = "null")
    private LocalDate endDate;
}
