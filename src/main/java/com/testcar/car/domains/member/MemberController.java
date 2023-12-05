package com.testcar.car.domains.member;


import com.testcar.car.common.annotation.AuthMember;
import com.testcar.car.common.annotation.RoleAllowed;
import com.testcar.car.common.response.PageResponse;
import com.testcar.car.domains.member.model.MemberResponse;
import com.testcar.car.domains.member.model.RegisterMemberRequest;
import com.testcar.car.domains.member.model.vo.MemberFilterCondition;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Operation(summary = "[사용자 관리] 사용자 조회 필터", description = "조건에 맞는 모든 사용자를 페이지네이션으로 조회합니다.")
    @GetMapping
    public PageResponse<MemberResponse> getMembersByCondition(
            @ParameterObject @ModelAttribute MemberFilterCondition condition,
            @ParameterObject Pageable pageable) {
        final Page<Member> members = memberService.findAllPageByCondition(condition, pageable);
        return PageResponse.from(members.map(MemberResponse::from));
    }

    @Operation(summary = "[사용자 관리] 사용자 상세 정보", description = "사용자의 상세 정보를 가져옵니다.")
    @GetMapping("/{memberId}")
    public MemberResponse getMemberById(@PathVariable Long memberId) {
        final Member member = memberService.findById(memberId);
        return MemberResponse.from(member);
    }

    @RoleAllowed(role = Role.ADMIN)
    @Operation(summary = "[사용자 관리] 계정 생성", description = "(관리자) 새로운 사용자 계정을 생성합니다.")
    @PostMapping("/register")
    public MemberResponse register(@Valid @RequestBody RegisterMemberRequest request) {
        final Member member = memberService.register(request);
        return MemberResponse.from(member);
    }

    @RoleAllowed(role = Role.ADMIN)
    @Operation(summary = "[사용자 관리] 사용자 정보 수정", description = "(관리자) 사용자 정보를 수정합니다.")
    @PatchMapping("/{memberId}")
    public MemberResponse update(
            @PathVariable Long memberId, @Valid @RequestBody RegisterMemberRequest request) {
        final Member member = memberService.updateById(memberId, request);
        return MemberResponse.from(member);
    }

    @RoleAllowed(role = Role.ADMIN)
    @Operation(summary = "[사용자 관리] 사용자 삭제", description = "(관리자) 사용자를 삭제합니다.")
    @DeleteMapping("/{memberId}")
    public MemberResponse withdraw(@PathVariable Long memberId) {
        final Member member = memberService.deleteById(memberId);
        return MemberResponse.from(member);
    }
}
