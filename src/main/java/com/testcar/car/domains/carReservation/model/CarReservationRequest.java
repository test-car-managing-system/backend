package com.testcar.car.domains.carReservation.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CarReservationRequest {
    @NotNull
    @Schema(description = "차량재고 ID", example = "1")
    private Long carStockId;
}
