package com.testcar.car.domains.member;


import com.testcar.car.common.annotation.AuthMember;
import com.testcar.car.domains.member.model.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    @GetMapping("/me")
    public MemberResponse getMe(@AuthMember Member member) {
        return MemberResponse.from(member);
    }
}
