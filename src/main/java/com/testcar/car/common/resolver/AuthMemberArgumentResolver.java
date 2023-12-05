package com.testcar.car.common.resolver;

import static com.testcar.car.domains.member.exception.ErrorCode.MEMBER_NOT_FOUND;

import com.testcar.car.common.annotation.AuthMember;
import com.testcar.car.common.auth.JwtService;
import com.testcar.car.common.exception.NotFoundException;
import com.testcar.car.domains.member.Member;
import com.testcar.car.domains.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class AuthMemberArgumentResolver implements HandlerMethodArgumentResolver {
    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthMember.class);
    }

    @Override
    @Nullable
    public Member resolveArgument(
            MethodParameter parameter,
            @Nullable ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            @Nullable WebDataBinderFactory binderFactory) {
        final Long userId = jwtService.getUserId();
        return memberRepository
                .findByIdAndDeletedFalse(userId)
                .orElseThrow(() -> new NotFoundException(MEMBER_NOT_FOUND));
    }
}
