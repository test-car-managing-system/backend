package com.testcar.car.domains.auth;


import com.testcar.car.common.auth.JwtService;
import com.testcar.car.common.exception.NotFoundException;
import com.testcar.car.common.exception.UnauthorizedException;
import com.testcar.car.domains.auth.model.LoginRequest;
import com.testcar.car.domains.auth.util.PasswordEncoder;
import com.testcar.car.domains.member.Member;
import com.testcar.car.domains.member.MemberRepository;
import com.testcar.car.domains.member.exception.ErrorCode;
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
        final Member member =
                memberRepository
                        .findByEmailAndDeletedFalse(request.getEmail())
                        .orElseThrow(() -> new NotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        final String memberPassword = member.getPassword();
        final String encodedPassword = PasswordEncoder.encode(request.getPassword());
        if (!memberPassword.equals(encodedPassword)) {
            throw new UnauthorizedException(ErrorCode.INVALID_PASSWORD);
        }
        return jwtService.generateAccessToken(member.getId());
    }
}
