package com.testcar.car.domains.department.entity;

import static com.testcar.car.common.Constant.DEPARTMENT_NAME;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class DepartmentTest {
    @Test
    public void 새로운_부서를_생성한다() {
        // given
        final String name = DEPARTMENT_NAME;

        // when
        final Department result = Department.builder().name(name).build();

        // then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(name);
    }
}
