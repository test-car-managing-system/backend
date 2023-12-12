package com.testcar.car.domains.carStock.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeleteCarStockRequest {
    @NotNull(message = "차량 재고 ID를 입력해주세요.")
    @NotEmpty(message = "차량 재고 ID를 입력해주세요.")
    @Schema(description = "차량 재고 ID", example = "[1, 2, 3]")
    private List<@NotNull @Positive Long> ids;
}
