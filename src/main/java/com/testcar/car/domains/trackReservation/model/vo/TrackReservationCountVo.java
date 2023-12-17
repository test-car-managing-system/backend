package com.testcar.car.domains.trackReservation.model.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Map.Entry;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TrackReservationCountVo {
    @Schema(description = "시험장명", example = "직선주행장")
    private String name;

    @Schema(description = "대여 횟수", example = "20")
    private long count;

    public static TrackReservationCountVo from(Entry<String, Long> entry) {
        return TrackReservationCountVo.builder()
                .name(entry.getKey())
                .count(entry.getValue())
                .build();
    }
}
