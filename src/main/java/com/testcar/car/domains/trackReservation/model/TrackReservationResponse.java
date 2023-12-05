package com.testcar.car.domains.trackReservation.model;


import com.testcar.car.common.annotation.DateFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TrackReservationResponse {
    @Schema(description = "시험장 예약 ID", example = "1")
    private Long id;

    @Schema(description = "시험장명", example = "서산주행시험장")
    private String name;

    @DateFormat
    @Schema(description = "예약일자", example = "2023-01-01")
    private LocalDate reservedAt;
}
