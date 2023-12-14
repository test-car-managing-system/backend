package com.testcar.car.domains.member.model.vo;


import com.testcar.car.domains.member.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberFilterCondition {
    @Schema(description = "사용자명", example = "홍길동")
    private String name;

    @Schema(description = "부서명", example = "시스템팀")
    private String departmentName;

    @Schema(description = "권한", example = "USER", implementation = Role.class)
    private Role role;
}
