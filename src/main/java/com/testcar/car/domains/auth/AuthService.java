package com.testcar.car.domains.auth;


import com.testcar.car.common.auth.JwtService;
import com.testcar.car.domains.auth.model.LoginRequest;
import com.testcar.car.domains.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    public String login(LoginRequest request) {
        return jwtService.generateAccessToken(1L);
    }
}
