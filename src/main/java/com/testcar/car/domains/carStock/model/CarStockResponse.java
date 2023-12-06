package com.testcar.car.domains.carStock.model;


import com.testcar.car.domains.carStock.entity.CarStock;
import com.testcar.car.domains.carStock.entity.StockStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CarStockResponse {
    @Schema(description = "차량 재고 ID", example = "1")
    private final Long id;

    @Schema(description = "차량명", example = "아반떼")
    private final String name;

    @Schema(description = "차량 재고번호", example = "2023010300001")
    private final String stockNumber;

    @Schema(description = "재고 상태", example = "AVAILABLE", implementation = StockStatus.class)
    private final StockStatus status;

    public static CarStockResponse from(CarStock carStock) {
        return CarStockResponse.builder()
                .id(carStock.getId())
                .name(carStock.getCar().getName())
                .stockNumber(carStock.getStockNumber())
                .status(carStock.getStatus())
                .build();
    }
}
