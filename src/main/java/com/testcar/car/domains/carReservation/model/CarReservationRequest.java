package com.testcar.car.domains.carReservation.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CarReservationRequest {
    @NotNull(message = "차량재고 ID를 입력해주세요.")
    @Positive(message = "차량재고 ID는 0보다 커야합니다.")
    @Schema(description = "차량재고 ID", example = "1")
    private Long carStockId;
}
