package com.testcar.car.domains.member.model;


import com.testcar.car.common.annotation.DateTimeFormat;
import com.testcar.car.domains.department.model.vo.DepartmentVo;
import com.testcar.car.domains.member.entity.Member;
import com.testcar.car.domains.member.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberResponse {
    @Schema(description = "사용자 ID", example = "1")
    private Long id;

    @Schema(description = "이메일", example = "kls1998@naver.com")
    private String email;

    @Schema(description = "이름", example = "홍길동")
    private String name;

    @Schema(description = "부서 정보")
    private DepartmentVo department;

    @Schema(description = "권한", example = "USER", implementation = Role.class)
    private Role role;

    @DateTimeFormat
    @Schema(description = "가입일자", example = "2021-08-01 12:00:12")
    private LocalDateTime createdAt;

    public static MemberResponse from(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .department(DepartmentVo.from(member.getDepartment()))
                .role(member.getRole())
                .createdAt(member.getCreatedAt())
                .build();
    }
}
