package com.testcar.car.domains.member;


import com.testcar.car.common.annotation.AuthMember;
import com.testcar.car.common.annotation.RoleAllowed;
import com.testcar.car.domains.member.model.MemberResponse;
import com.testcar.car.domains.member.model.RegisterMemberRequest;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "[사용자 관리] 내 정보", description = "나의 정보를 가져옵니다.")
    @GetMapping("/me")
    public MemberResponse getMe(@AuthMember Member member) {
        return MemberResponse.from(member);
    }

    @RoleAllowed(role = Role.ADMIN)
    @Operation(summary = "[사용자 관리] 계정 생성", description = "새로운 사용자 계정을 생성합니다.")
    @PostMapping("/register")
    public MemberResponse register(@Valid @RequestBody RegisterMemberRequest request) {
        final Member member = memberService.register(request);
        return MemberResponse.from(member);
    }
}
