package com.testcar.car.domains.carReservation.model.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Map.Entry;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CarReservationCountVo {
    @Schema(description = "차량명", example = "아반떼")
    private String name;

    @Schema(description = "대여 횟수", example = "20")
    private long count;

    public static CarReservationCountVo from(Entry<String, Long> entry) {
        return CarReservationCountVo.builder().name(entry.getKey()).count(entry.getValue()).build();
    }
}
