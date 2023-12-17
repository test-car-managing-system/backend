package com.testcar.car.domains.trackReservation.model;


import com.testcar.car.domains.trackReservation.model.vo.TrackReservationCountVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TrackReservationRankingResponse {
    @Schema(description = "시험장명", example = "직선주행장")
    private final String name;

    @Schema(description = "대여 횟수", example = "20")
    private final long count;

    public static TrackReservationRankingResponse from(
            TrackReservationCountVo trackReservationCountVo) {
        return TrackReservationRankingResponse.builder()
                .name(trackReservationCountVo.getName())
                .count(trackReservationCountVo.getCount())
                .build();
    }
}
