package com.testcar.car.domains.carReservation.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;

@Getter
public class ReturnCarReservationRequest {
    @NotNull
    @Schema(description = "차량 예약 ID 리스트", example = "[1, 2, 3]")
    private List<Long> carReservationIds;
}
