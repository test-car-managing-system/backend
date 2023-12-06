package com.testcar.car.domains.car.model;


import com.testcar.car.common.annotation.DateTimeFormat;
import com.testcar.car.domains.car.entity.Car;
import com.testcar.car.domains.car.entity.Type;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CarResponse {
    @Schema(description = "차량 ID", example = "1")
    private Long id;

    @Schema(description = "차량명", example = "아반떼")
    private String name;

    @Schema(description = "차종", example = "SEDAN", implementation = Type.class)
    private Type type;

    @Schema(description = "배기량", example = "1.6")
    private Double displacement;

    @DateTimeFormat
    @Schema(description = "등록일시", example = "2021-01-01 12:33:22")
    private LocalDateTime createdAt;

    public static CarResponse from(Car car) {
        return CarResponse.builder()
                .id(car.getId())
                .name(car.getName())
                .type(car.getType())
                .displacement(car.getDisplacement())
                .createdAt(car.getCreatedAt())
                .build();
    }
}
