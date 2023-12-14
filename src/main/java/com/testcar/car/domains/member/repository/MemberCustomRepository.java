package com.testcar.car.domains.member.repository;


import com.testcar.car.domains.member.entity.Member;
import com.testcar.car.domains.member.model.vo.MemberFilterCondition;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberCustomRepository {
    Optional<Member> findById(Long id);

    Page<Member> findAllPageByCondition(MemberFilterCondition condition, Pageable pageable);
}
