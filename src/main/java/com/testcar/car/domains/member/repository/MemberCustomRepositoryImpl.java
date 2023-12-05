package com.testcar.car.domains.member.repository;


import com.testcar.car.domains.member.Member;
import java.util.Optional;

public class MemberCustomRepositoryImpl implements MemberCustomRepository {

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.empty();
    }
}
