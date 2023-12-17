package com.testcar.car.domains.carReservation.model;


import com.testcar.car.domains.carReservation.model.vo.CarReservationCountVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CarReservationRankingResponse {
    @Schema(description = "차량명", example = "아반떼")
    private final String name;

    @Schema(description = "대여 횟수", example = "20")
    private final long count;

    public static CarReservationRankingResponse from(CarReservationCountVo carReservationCountVo) {
        return CarReservationRankingResponse.builder()
                .name(carReservationCountVo.getName())
                .count(carReservationCountVo.getCount())
                .build();
    }
}
