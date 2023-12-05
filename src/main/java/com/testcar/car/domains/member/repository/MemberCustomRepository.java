package com.testcar.car.domains.member.repository;


import com.testcar.car.domains.member.Member;
import java.util.Optional;

public interface MemberCustomRepository {
    Optional<Member> findById(Long id);
}
