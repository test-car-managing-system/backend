package com.testcar.car.domains.carStock.model.vo;


import com.testcar.car.domains.carStock.entity.StockStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarStockFilterCondition {
    @Schema(description = "차량 id", example = "null")
    private Long carId;

    @Schema(description = "차량명", example = "null")
    private String name;

    @Schema(description = "재고번호", example = "null")
    private String stockNumber;

    @Schema(description = "재고상태", example = "null", implementation = StockStatus.class)
    private StockStatus status;
}
