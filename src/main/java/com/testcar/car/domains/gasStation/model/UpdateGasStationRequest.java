package com.testcar.car.domains.gasStation.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateGasStationRequest {
    @NotBlank(message = "주유소명을 입력해주세요.")
    @Schema(description = "주유소명", example = "서산주유소A")
    private String name;
}
