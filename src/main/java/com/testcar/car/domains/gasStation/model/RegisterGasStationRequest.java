package com.testcar.car.domains.gasStation.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class RegisterGasStationRequest {
    @NotBlank
    @Schema(description = "주유소명", example = "서산주유소A")
    private String name;
}
