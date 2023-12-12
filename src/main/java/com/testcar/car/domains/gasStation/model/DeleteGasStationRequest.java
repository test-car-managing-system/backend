package com.testcar.car.domains.gasStation.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.Getter;

@Getter
public class DeleteGasStationRequest {
    @NotNull(message = "주유소 ID 리스트를 입력해주세요.")
    @NotEmpty(message = "주유소 ID 리스트를 입력해주세요.")
    @Schema(description = "주유소 ID", example = "[1, 2, 3]")
    private List<@Valid @NotNull @Positive Long> ids;
}
