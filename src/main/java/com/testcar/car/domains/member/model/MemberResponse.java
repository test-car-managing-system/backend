package com.testcar.car.domains.member.model;


import com.testcar.car.domains.member.Member;
import com.testcar.car.domains.member.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberResponse {
    private Long id;
    private String email;
    private String name;
    private Role role;

    public static MemberResponse from(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .role(member.getRole())
                .build();
    }
}
