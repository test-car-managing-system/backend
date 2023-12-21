package com.testcar.car.domains.track.model;


import com.testcar.car.domains.track.entity.Track;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TrackResponse {
    @Schema(description = "시험장 ID", example = "1")
    private Long id;

    @Schema(description = "시험명", example = "서산주행시험장")
    private String name;

    @Schema(description = "위치", example = "충청남도 서산시 부석면")
    private String location;

    @Schema(description = "경도", example = "132.123123")
    private Double longitude;

    @Schema(description = "위도", example = "37.123123")
    private Double latitude;

    @Schema(description = "시험장 날씨", example = "맑음")
    private String weather;

    @Schema(description = "기온", example = "10")
    private Integer temperature;

    @Schema(description = "시험장 특성", example = "평지")
    private String description;

    @Schema(description = "길이", example = "100.32")
    private Double length;

    public static TrackResponse from(Track track) {
        return TrackResponse.builder()
                .id(track.getId())
                .name(track.getName())
                .location(track.getLocation())
                .longitude(track.getLongitude())
                .latitude(track.getLatitude())
                .weather(track.getWeather())
                .temperature(track.getTemperature())
                .description(track.getDescription())
                .length(track.getLength())
                .build();
    }
}
