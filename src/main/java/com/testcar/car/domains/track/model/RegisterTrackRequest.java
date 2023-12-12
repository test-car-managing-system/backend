package com.testcar.car.domains.track.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class RegisterTrackRequest {
    @NotBlank(message = "시험장명을 입력해주세요.")
    @Length(max = 20, message = "시험장명은 20자 이하로 입력해주세요.")
    @Schema(description = "시험장명", example = "서산주행시험장")
    private String name;

    @NotBlank(message = "위치를 입력해주세요.")
    @Schema(description = "위치", example = "충청남도 서산시 부석면")
    private String location;

    @NotBlank(message = "시험장 특성을 입력해주세요.")
    @Schema(description = "시험장 특성", example = "평지")
    private String description;

    @NotNull(message = "시험장 길이를 입력해주세요.")
    @Positive(message = "시험장 길이는 0보다 커야합니다.")
    @Schema(description = "길이", example = "100.32")
    private Double length;
}
