package com.testcar.car.domains.gasStationHistory.model;


import com.testcar.car.common.annotation.DateFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class RegisterGasStationHistoryRequest {
    @NotBlank(message = "주유소 이름을 입력해주세요.")
    @Schema(description = "주유소 이름", example = "서산주유소A")
    private String gasStationName;

    @NotBlank(message = "재고번호를 입력해주세요.")
    @Pattern(regexp = "^[0-9]{12}$", message = "12자리 숫자만 입력 가능합니다.")
    @Schema(description = "재고번호", example = "123412341234")
    private String stockNumber;

    @NotNull(message = "주유량을 입력해주세요.")
    @Positive(message = "주유량은 0보다 커야합니다.")
    @Schema(description = "주유량", example = "100.33")
    private Double amount;

    @DateFormat
    @NotNull(message = "주유일자를 입력해주세요.")
    @Schema(description = "주유일자", example = "2021-01-01")
    private LocalDate usedAt;
}
