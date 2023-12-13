package com.testcar.car.domains.department.request;

import static com.testcar.car.common.Constant.DEPARTMENT_NAME;

import com.testcar.car.domains.department.model.CreateDepartmentRequest;

public class DepartmentRequestFactory {
    private DepartmentRequestFactory() {}

    public static CreateDepartmentRequest createDepartmentRequestFactory() {
        return CreateDepartmentRequest.builder().name(DEPARTMENT_NAME).build();
    }
}
