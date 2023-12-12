package com.testcar.car.domains.department.model.vo;


import com.testcar.car.domains.department.entity.Department;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DepartmentVo {
    private Long id;
    private String name;

    public static DepartmentVo from(Department department) {
        return DepartmentVo.builder().id(department.getId()).name(department.getName()).build();
    }
}
