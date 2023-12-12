package com.testcar.car.domains.car.model;


import com.testcar.car.domains.car.entity.Type;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
@Builder
public class RegisterCarRequest {
    @NotBlank(message = "차량명을 입력해주세요.")
    @Length(max = 20)
    @Schema(description = "차량명", example = "아반떼")
    private String name;

    @NotNull(message = "차종을 입력해주세요.")
    @Schema(description = "차종", example = "SEDAN", implementation = Type.class)
    private Type type;

    @NotNull(message = "배기량을 입력해주세요.")
    @Positive(message = "배기량은 0보다 커야합니다.")
    @Schema(description = "배기량", example = "1.6")
    private Double displacement;
}
