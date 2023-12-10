package com.testcar.car.domains.carReservation.model;


import com.testcar.car.domains.car.entity.Type;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class CarReservationRequest {
    @NotNull
    @Schema(description = "차량재고 ID", example = "1")
    private Long carStockId;
}
