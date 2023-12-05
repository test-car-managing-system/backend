package com.testcar.car.domains.track.model.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackFilterCondition {
    @Schema(description = "시험장명", example = "null")
    private String name;

    @Schema(description = "위치", example = "null")
    private String location;
}
