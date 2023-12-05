package com.testcar.car.domains.member;


import com.testcar.car.common.exception.BadRequestException;
import com.testcar.car.common.exception.NotFoundException;
import com.testcar.car.domains.auth.util.PasswordEncoder;
import com.testcar.car.domains.department.Department;
import com.testcar.car.domains.department.DepartmentService;
import com.testcar.car.domains.member.exception.ErrorCode;
import com.testcar.car.domains.member.model.RegisterMemberRequest;
import com.testcar.car.domains.member.model.vo.MemberFilterCondition;
import com.testcar.car.domains.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final DepartmentService departmentService;
    private final MemberRepository memberRepository;

    /** 사용자를 id로 조회합니다. */
    public Member findById(Long id) {
        return memberRepository
                .findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }

    /** 사용자를 조건에 맞게 조회합니다. */
    public Page<Member> findAllPageByCondition(MemberFilterCondition condition, Pageable pageable) {
        return memberRepository.findAllPageByCondition(condition);
    }

    /** 새로운 계정을 등록합니다. */
    public Member register(RegisterMemberRequest request) {
        validateEmailNotDuplicated(request.getEmail());
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

    /** 계정을 삭제합니다. */
    public Member update(RegisterMemberRequest request) {
        validateEmailNotDuplicated(request.getEmail());
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

    /** 이메일 중복을 검사합니다. */
    private void validateEmailNotDuplicated(String email) {
        if (memberRepository.existsByEmailAndDeletedFalse(email)) {
            throw new BadRequestException(ErrorCode.DUPLICATED_EMAIL);
        }
    }
}
