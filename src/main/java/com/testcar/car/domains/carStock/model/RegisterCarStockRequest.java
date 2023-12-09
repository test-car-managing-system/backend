package com.testcar.car.domains.carStock.model;


import com.testcar.car.domains.carStock.entity.StockStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class RegisterCarStockRequest {
    @NotNull
    @Schema(description = "차량 ID", example = "아반떼")
    private Long carId;

    @NotBlank
    @Pattern(regexp = "^[0-9]{12}$")
    @Schema(description = "차량 재고번호", example = "2023010300001")
    private String stockNumber;

    @NotNull
    @Schema(description = "재고 상태", example = "AVAILABLE", implementation = StockStatus.class)
    private StockStatus status;
}
