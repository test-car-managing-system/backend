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
public class RegisterMemberRequest {
    @NotNull(message = "부서 ID를 입력해주세요.")
    @Positive(message = "부서 ID는 0보다 커야합니다.")
    @Schema(description = "부서 ID", example = "1")
    private Long departmentId;

    @Length(max = 50, message = "이메일은 50자 이하로 입력해주세요.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @NotBlank(message = "이메일을 입력해주세요.")
    @Schema(description = "이메일", example = "kls1998@naver.com")
    private String email;

    @Password
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Schema(description = "비밀번호", example = "1234abcd@")
    private String password;

    @NotBlank(message = "이름을 입력해주세요.")
    @Schema(description = "이름", example = "홍길동")
    private String name;

    @NotNull(message = "권한을 입력해주세요.")
    @Schema(description = "권한", example = "USER", implementation = Role.class)
    private Role role;
}
