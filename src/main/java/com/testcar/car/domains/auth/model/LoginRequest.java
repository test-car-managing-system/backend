package com.testcar.car.domains.auth.model;


import com.testcar.car.common.annotation.Password;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {
    @NotBlank
    @Schema(description = "이메일", example = "kls1998@naver.com")
    private String email;

    @Password
    @Schema(description = "비밀번호", example = "1234abcd@")
    private String password;
}
