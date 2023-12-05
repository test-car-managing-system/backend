package com.testcar.car.domains.department.model;


import com.testcar.car.domains.department.Department;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DepartmentResponse {
    @Schema(description = "부서 ID", example = "1")
    private Long id;

    @Schema(description = "부서명", example = "시스템관리팀")
    private String name;

    public static DepartmentResponse from(Department department) {
        return DepartmentResponse.builder()
                .id(department.getId())
                .name(department.getName())
                .build();
    }
}
