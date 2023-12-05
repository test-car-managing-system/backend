package com.testcar.car.domains.member.model;


import com.testcar.car.common.annotation.Password;
import com.testcar.car.domains.member.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class UpdateMemberRequest {
    @NotNull
    @Positive
    @Schema(description = "부서 ID", example = "1")
    private Long departmentId;

    @Email
    @NotBlank
    @Length(max = 50)
    @Schema(description = "이메일", example = "kls1998@naver.com")
    private String email;

    @Password
    @Schema(description = "비밀번호", example = "1234abcd@")
    private String password;

    @NotBlank
    @Schema(description = "이름", example = "홍길동")
    private String name;

    @NotNull
    @Schema(description = "권한", example = "USER", implementation = Role.class)
    private Role role;
}
