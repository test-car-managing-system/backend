package com.testcar.car.domains.member;


import com.testcar.car.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member findById(Long id) {
        return memberRepository
                .findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
