package com.testcar.car.domains.member;


import com.testcar.car.common.exception.NotFoundException;
import com.testcar.car.domains.auth.util.PasswordEncoder;
import com.testcar.car.domains.department.Department;
import com.testcar.car.domains.department.DepartmentService;
import com.testcar.car.domains.member.exception.ErrorCode;
import com.testcar.car.domains.member.model.RegisterMemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final DepartmentService departmentService;
    private final MemberRepository memberRepository;

    public Member findById(Long id) {
        return memberRepository
                .findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }

    public Member register(RegisterMemberRequest request) {
        final Department department = departmentService.findById(request.getDepartmentId());
        final String encodedPassword = PasswordEncoder.encode(request.getPassword());
        final Member member =
                Member.builder()
                        .department(department)
                        .email(request.getEmail())
                        .password(encodedPassword)
                        .name(request.getName())
                        .role(request.getRole())
                        .build();
        return memberRepository.save(member);
    }
}
