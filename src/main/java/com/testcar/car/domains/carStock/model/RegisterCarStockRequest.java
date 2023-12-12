package com.testcar.car.domains.carStock.model;


import com.testcar.car.domains.carStock.entity.StockStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterCarStockRequest {
    @NotNull(message = "차량 ID를 입력해주세요.")
    @Positive(message = "차량 ID는 0보다 커야합니다.")
    @Schema(description = "차량 ID", example = "아반떼")
    private Long carId;

    @NotBlank(message = "차량 재고번호를 입력해주세요.")
    @Pattern(regexp = "^[0-9]{12}$", message = "차량 재고번호는 12자리 숫자만 가능합니다.")
    @Schema(description = "차량 재고번호", example = "2023010300001")
    private String stockNumber;

    @NotNull(message = "차량 재고 상태를 입력해주세요.")
    @Schema(description = "재고 상태", example = "AVAILABLE", implementation = StockStatus.class)
    private StockStatus status;
}
