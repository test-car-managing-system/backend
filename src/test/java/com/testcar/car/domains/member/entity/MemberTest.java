package com.testcar.car.domains.member.entity;

import static com.testcar.car.common.Constant.ANOTHER_MEMBER_NAME;
import static com.testcar.car.common.Constant.MEMBER_EMAIL;
import static com.testcar.car.common.Constant.MEMBER_NAME;
import static com.testcar.car.common.Constant.MEMBER_PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;

import com.testcar.car.common.MemberEntityFactory;
import com.testcar.car.domains.department.entity.Department;
import org.junit.jupiter.api.Test;

public class MemberTest {
    @Test
    public void 새로운_사용자를_생성한다() {
        // given
        final String name = MEMBER_NAME;
        final Department department = MemberEntityFactory.createDepartment();
        final String password = MEMBER_PASSWORD;
        final String email = MEMBER_EMAIL;
        final Role role = Role.ADMIN;

        // when
        final Member member =
                Member.builder()
                        .name(name)
                        .department(department)
                        .password(password)
                        .email(email)
                        .role(role)
                        .build();
        // then
        assertThat(member.getName()).isEqualTo(name);
        assertThat(member.getDepartment()).isEqualTo(department);
        assertThat(member.getPassword()).isEqualTo(password);
        assertThat(member.getEmail()).isEqualTo(email);
        assertThat(member.getRole()).isEqualTo(role);
    }

    @Test
    public void 사용자_정보를_변경한다() {
        // given
        final Member member = MemberEntityFactory.createMember();
        final Member updatedMember =
                Member.builder()
                        .name(ANOTHER_MEMBER_NAME)
                        .department(MemberEntityFactory.createDepartment())
                        .password(MEMBER_PASSWORD)
                        .email(MEMBER_EMAIL)
                        .role(Role.USER)
                        .build();

        // when
        member.update(updatedMember);

        // then
        assertThat(member.getName()).isEqualTo(updatedMember.getName());
        assertThat(member.getDepartment()).isEqualTo(updatedMember.getDepartment());
        assertThat(member.getPassword()).isEqualTo(updatedMember.getPassword());
        assertThat(member.getEmail()).isEqualTo(updatedMember.getEmail());
        assertThat(member.getRole()).isEqualTo(updatedMember.getRole());
    }
}
