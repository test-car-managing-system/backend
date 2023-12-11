package com.testcar.car.domains.gasStationHistory.model;


import com.testcar.car.common.annotation.DateFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class RegisterGasStationHistoryRequest {
    @NotBlank
    @Schema(description = "주유소 이름", example = "서산주유소A")
    private String gasStationName;

    @Schema(description = "재고번호", example = "123412341234")
    private String stockNumber;

    @NotNull
    @Schema(description = "주유량", example = "100.33")
    private Double amount;

    @NotNull
    @DateFormat
    @Schema(description = "주유일자", example = "2021-01-01")
    private LocalDate usedAt;
}
