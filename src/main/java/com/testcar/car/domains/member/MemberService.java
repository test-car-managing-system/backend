package com.testcar.car.domains.member;


import com.testcar.car.common.exception.BadRequestException;
import com.testcar.car.common.exception.NotFoundException;
import com.testcar.car.domains.auth.util.PasswordEncoder;
import com.testcar.car.domains.department.DepartmentService;
import com.testcar.car.domains.department.entity.Department;
import com.testcar.car.domains.member.exception.ErrorCode;
import com.testcar.car.domains.member.model.RegisterMemberRequest;
import com.testcar.car.domains.member.model.UpdateMemberRequest;
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
        return memberRepository.findAllPageByCondition(condition, pageable);
    }

    /** 새로운 계정을 등록합니다. */
    public Member register(RegisterMemberRequest request) {
        validateEmailNotDuplicated(request.getEmail());
        final Member member = createEntity(request);
        return memberRepository.save(member);
    }

    /** 계정 정보를 업데이트 합니다. */
    public Member updateById(Long memberId, UpdateMemberRequest request) {
        final Member member = this.findById(memberId);
        final Member updateMember = createEntity(request);
        if (!member.getEmail().equals(updateMember.getEmail())) {
            validateEmailNotDuplicated(updateMember.getEmail());
        }
        member.update(updateMember);
        return memberRepository.save(member);
    }

    /** 계정을 삭제 처리 합니다. (soft delete) */
    public Member deleteById(Member member, Long memberId) {
        if (member.getId() == memberId) {
            throw new BadRequestException(ErrorCode.CANNOT_DELETE_MYSELF);
        }
        final Member deleteMember = this.findById(memberId);
        deleteMember.delete();
        return memberRepository.save(deleteMember);
    }

    /** 영속되지 않은 멤버 엔티티를 생성합니다. */
    private Member createEntity(RegisterMemberRequest request) {
        final Department department = departmentService.findById(request.getDepartmentId());
        return Member.builder()
                .department(department)
                .email(request.getEmail())
                .password(PasswordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .role(request.getRole())
                .build();
    }

    private Member createEntity(UpdateMemberRequest request) {
        final Department department = departmentService.findById(request.getDepartmentId());
        return Member.builder()
                .department(department)
                .email(request.getEmail())
                .name(request.getName())
                .role(request.getRole())
                .build();
    }

    /** 이메일 중복을 검사합니다. */
    private void validateEmailNotDuplicated(String email) {
        if (memberRepository.existsByEmailAndDeletedFalse(email)) {
            throw new BadRequestException(ErrorCode.DUPLICATED_EMAIL);
        }
    }
}
