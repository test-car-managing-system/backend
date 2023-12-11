package com.testcar.car.domains.gasStation.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Getter;

@Getter
public class DeleteGasStationRequest {
    @NotEmpty
    @Schema(description = "주유소 ID", example = "[1, 2, 3]")
    private List<Long> ids;
}
