package com.testcar.car.common.interceptor;

import static com.testcar.car.domains.member.exception.ErrorCode.MEMBER_NOT_FOUND;
import static com.testcar.car.domains.member.exception.ErrorCode.UNAUTHORIZED_USER;

import com.testcar.car.common.annotation.RoleAllowed;
import com.testcar.car.common.auth.JwtService;
import com.testcar.car.common.exception.NotFoundException;
import com.testcar.car.common.exception.UnauthorizedException;
import com.testcar.car.domains.member.Member;
import com.testcar.car.domains.member.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/** 해당 서비스에 대한 접근 권한을 통제하는 인터셉터 */
@Component
@RequiredArgsConstructor
public class MemberRoleInterceptor implements HandlerInterceptor {
    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod handlerMethod) {
            final RoleAllowed role = handlerMethod.getMethodAnnotation(RoleAllowed.class);
            if (role == null) {
                return true;
            }

            final Long memberId = jwtService.getMemberId();
            final Member member =
                    memberRepository
                            .findByIdAndDeletedFalse(memberId)
                            .orElseThrow(() -> new NotFoundException(MEMBER_NOT_FOUND));
            if (member.getRole() != role.role()) {
                throw new UnauthorizedException(UNAUTHORIZED_USER);
            }
        }
        return true;
    }
}
