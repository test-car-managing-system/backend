package com.testcar.car.domains.car.model;


import com.testcar.car.common.annotation.DateFormat;
import com.testcar.car.domains.car.entity.Car;
import com.testcar.car.domains.car.entity.Type;
import com.testcar.car.domains.carStock.entity.StockStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TestCarResponse {
    @Schema(description = "차량 ID", example = "1")
    private Long id;

    @Schema(description = "차량명", example = "아반떼")
    private String name;

    @Schema(description = "배기량", example = "1.6")
    private Double displacement;

    @Schema(description = "차종", example = "SEDAN", implementation = Type.class)
    private Type type;

    @Schema(description = "남은 수량", example = "1")
    private Long stock;

    @Schema(
            description = "재고 상태",
            example = "대여 가능",
            allowableValues = {"대여 가능", "재고 없음"})
    private String status;

    @DateFormat
    @Schema(description = "등록일자", example = "2021-01-01")
    private LocalDateTime createdAt;

    public static TestCarResponse from(Car car) {
        final long stockCount =
                car.getCarStocks().stream()
                        .filter(carStock -> carStock.getStatus() == StockStatus.AVAILABLE)
                        .count();
        final String status = stockCount > 0 ? "대여 가능" : "재고 없음";
        return TestCarResponse.builder()
                .id(car.getId())
                .name(car.getName())
                .type(car.getType())
                .stock(stockCount)
                .displacement(car.getDisplacement())
                .status(status)
                .createdAt(car.getCreatedAt())
                .build();
    }
}
