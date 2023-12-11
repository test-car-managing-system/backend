package com.testcar.car.domains.carTest.model;


import com.testcar.car.common.annotation.DateFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class CarTestRequest {
    @NotBlank
    @Schema(description = "시험장 이름", example = "서산주행시험장")
    private String trackName;

    @Schema(description = "차량 재고 번호", example = "123412343333")
    private String stockNumber;

    @DateFormat
    @Schema(description = "시험일자", example = "2021-01-01")
    private LocalDate performedAt;

    @Schema(description = "주행결과", example = "이상없음")
    private String result;

    @Schema(description = "비고", example = "타이어 정비가 필요해보임")
    private String memo;
}
