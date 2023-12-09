package com.testcar.car.domains.carStock.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.Getter;

@Getter
public class DeleteCarStockRequest {
    @NotEmpty
    @Schema(description = "차량 재고 ID", example = "[1, 2, 3]")
    private List<@NotNull @Positive Long> ids;
}
