package com.testcar.car.domains.auth;

import static com.testcar.car.common.Constant.MEMBER_PASSWORD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.testcar.car.common.MemberEntityFactory;
import com.testcar.car.common.auth.JwtService;
import com.testcar.car.common.exception.NotFoundException;
import com.testcar.car.common.exception.UnauthorizedException;
import com.testcar.car.domains.auth.model.LoginRequest;
import com.testcar.car.domains.auth.request.AuthRequestFactory;
import com.testcar.car.domains.auth.util.PasswordEncoder;
import com.testcar.car.domains.member.entity.Member;
import com.testcar.car.domains.member.repository.MemberRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock private JwtService jwtService;
    @Mock private MemberRepository memberRepository;
    @InjectMocks private AuthService authService;

    private static Member member;
    private static final String encodedPassword = PasswordEncoder.encode(MEMBER_PASSWORD);
    private static final String token = "generatedToken";

    @BeforeAll
    public static void setUp() {
        member = MemberEntityFactory.createMemberBuilder().password(encodedPassword).build();
    }

    @Test
    public void 로그인_성공_테스트() {
        // given
        final LoginRequest request = AuthRequestFactory.createLoginRequest();
        when(memberRepository.findByEmailAndDeletedFalse(request.getEmail()))
                .thenReturn(Optional.of(member));
        when(jwtService.generateAccessToken(member.getId())).thenReturn(token);

        // when
        String result = authService.login(request);

        // then
        assertNotNull(result);
        assertEquals(token, result);
    }

    @Test
    public void 해당_아이디의_사용자가_DB에_존재하지_않으면_예외가_발생한다() {
        // given
        final LoginRequest request = AuthRequestFactory.createLoginRequest();
        when(memberRepository.findByEmailAndDeletedFalse(request.getEmail()))
                .thenReturn(Optional.empty());

        // when, then
        assertThrows(
                NotFoundException.class,
                () -> {
                    authService.login(request);
                });
    }

    @Test
    public void 잘못된_비밀번호를_입력하면_예외가_발생한다() {
        // given
        final LoginRequest invaildRequest = AuthRequestFactory.createInvalidLoginRequest();
        when(memberRepository.findByEmailAndDeletedFalse(invaildRequest.getEmail()))
                .thenReturn(Optional.of(member));

        // when, then
        assertThrows(
                UnauthorizedException.class,
                () -> {
                    authService.login(invaildRequest);
                });
    }
}
