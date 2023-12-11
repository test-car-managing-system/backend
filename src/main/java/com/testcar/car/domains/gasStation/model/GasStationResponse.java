package com.testcar.car.domains.gasStation.model;


import com.testcar.car.common.annotation.DateTimeFormat;
import com.testcar.car.domains.gasStation.entity.GasStation;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GasStationResponse {
    @Schema(description = "주유소 ID", example = "1")
    private final Long id;

    @Schema(description = "주유소명", example = "서산주유소A")
    private final String name;

    @DateTimeFormat
    @Schema(description = "등록일자", example = "2021-01-01 00:00:00")
    private final LocalDateTime createdAt;

    public static GasStationResponse from(GasStation gasStation) {
        return GasStationResponse.builder()
                .id(gasStation.getId())
                .name(gasStation.getName())
                .createdAt(gasStation.getCreatedAt())
                .build();
    }
}
