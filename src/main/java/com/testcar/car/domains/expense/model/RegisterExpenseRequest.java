package com.testcar.car.domains.expense.model;


import com.testcar.car.common.annotation.DateFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterExpenseRequest {
    @NotBlank(message = "지출내용을 입력해주세요.")
    @Schema(description = "지출내용", example = "차량수선비")
    private String description;

    @Schema(description = "재고번호", example = "123412341234")
    private String stockNumber;

    @DateFormat
    @NotNull(message = "지출일자를 입력해주세요.")
    @Schema(description = "지출일자", example = "2021-01-01")
    private LocalDate usedAt;

    @NotNull(message = "금액을 입력해주세요.")
    @Positive(message = "금액은 0 보다 커야합니다.")
    @Schema(description = "지출금액", example = "100")
    private Long amount;
}
