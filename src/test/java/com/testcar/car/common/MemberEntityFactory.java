
package com.testcar.car.common;

import static com.testcar.car.common.Constant.DEPARTMENT_NAME;
import static com.testcar.car.common.Constant.MEMBER_EMAIL;
import static com.testcar.car.common.Constant.MEMBER_NAME;
import static com.testcar.car.common.Constant.MEMBER_PASSWORD;
import static com.testcar.car.common.Constant.MEMBER_ROLE;

import com.testcar.car.domains.department.entity.Department;
import com.testcar.car.domains.member.Member;
import com.testcar.car.domains.member.Member.MemberBuilder;

public class MemberEntityFactory {
    private MemberEntityFactory() {
    }


    public static Member createMember() {
        return createMemberBuilder().build();
    }

    public static MemberBuilder createMemberBuilder() {
        return Member.builder()
                .email(MEMBER_EMAIL)
                .password(MEMBER_PASSWORD)
                .name(MEMBER_NAME)
                .department(createDepartment())
                .role(MEMBER_ROLE);
    }

    public static Department createDepartment() {
        return createDepartmentBuilder().build();
    }

    public static Department.DepartmentBuilder createDepartmentBuilder() {
        return Department.builder().name(DEPARTMENT_NAME);
    }
}
