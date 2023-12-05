package com.testcar.car.domains.department.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CreateDepartmentRequest {
    @Schema(description = "부서명", example = "시스템관리팀")
    private String name;
}
