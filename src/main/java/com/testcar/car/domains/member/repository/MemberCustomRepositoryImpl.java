package com.testcar.car.domains.member.repository;

import static com.testcar.car.domains.department.QDepartment.department;
import static com.testcar.car.domains.member.QMember.member;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.testcar.car.common.entity.BaseQueryDslRepository;
import com.testcar.car.domains.member.Member;
import com.testcar.car.domains.member.Role;
import com.testcar.car.domains.member.model.vo.MemberFilterCondition;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository, BaseQueryDslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Page<Member> findAllPageByCondition(MemberFilterCondition condition, Pageable pageable) {
        final List<Long> coveringIndex =
                jpaQueryFactory
                        .select(member.id)
                        .from(member)
                        .where(
                                notDeleted(member),
                                memberNameContainsOrNull(condition.getName()),
                                departmentNameContainsOrNull(condition.getDepartmentName()),
                                roleEqOrNull(condition.getRole()))
                        .fetch();

        final List<Member> members =
                jpaQueryFactory
                        .selectFrom(member)
                        .leftJoin(member.department, department)
                        .fetchJoin()
                        .where(member.id.in(coveringIndex))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();
        return PageableExecutionUtils.getPage(members, pageable, coveringIndex::size);
    }

    private BooleanExpression memberNameContainsOrNull(String name) {
        return name == null ? null : member.name.contains(name);
    }

    private BooleanExpression departmentNameContainsOrNull(String name) {
        return name == null ? null : member.department.name.contains(name);
    }

    private BooleanExpression roleEqOrNull(Role role) {
        return role == null ? null : member.role.eq(role);
    }
}
