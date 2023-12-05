package com.testcar.car.domains.member;


import com.testcar.car.domains.member.repository.MemberCustomRepository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberCustomRepository {
    Optional<Member> findByIdAndDeletedFalse(Long id);

    Optional<Member> findByEmailAndDeletedFalse(String email);
}
