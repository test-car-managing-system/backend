package com.testcar.car.domains.car.model;


import com.testcar.car.domains.car.entity.Type;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class RegisterCarRequest {
    @NotBlank
    @Length(max = 20)
    @Schema(description = "차량명", example = "아반떼")
    private String name;

    @NotNull
    @Schema(description = "차종", example = "SEDAN", implementation = Type.class)
    private Type type;

    @NotNull
    @Positive
    @Schema(description = "배기량", example = "1.6")
    private Double displacement;
}
