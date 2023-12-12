package com.testcar.car.domains.department.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateDepartmentRequest {
    @NotBlank(message = "부서명을 입력해주세요.")
    @Schema(description = "부서명", example = "시스템관리팀")
    private String name;
}
