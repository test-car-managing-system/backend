package com.testcar.car.domains.track.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class RegisterTrackRequest {
    @NotBlank
    @Length(max = 20)
    @Schema(description = "시험장명", example = "서산주행시험장")
    private String name;

    @NotBlank
    @Schema(description = "위치", example = "충청남도 서산시 부석면")
    private String location;
}
