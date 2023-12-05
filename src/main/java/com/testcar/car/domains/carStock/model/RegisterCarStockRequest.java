package com.testcar.car.domains.carStock.model;


import com.testcar.car.domains.carStock.StockStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class RegisterCarStockRequest {
    @NotBlank
    @Length(max = 20)
    @Schema(description = "차량명", example = "아반떼")
    private String name;

    @NotBlank
    @Pattern(regexp = "^[0-9]{13}$")
    @Schema(description = "차량 재고번호", example = "2023010300001")
    private String stockNumber;

    @NotNull
    @Schema(description = "재고 상태", example = "AVAILABLE", implementation = StockStatus.class)
    private StockStatus status;
}