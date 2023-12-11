package com.testcar.car.domains.expense.repository;

import static com.testcar.car.domains.carStock.entity.QCarStock.carStock;
import static com.testcar.car.domains.expense.entity.QExpense.expense;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DatePath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.testcar.car.common.entity.BaseQueryDslRepository;
import com.testcar.car.domains.expense.model.dto.ExpenseDto;
import com.testcar.car.domains.expense.model.vo.ExpenseFilterCondition;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class ExpenseCustomRepositoryImpl
        implements ExpenseCustomRepository, BaseQueryDslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<ExpenseDto> findDetailById(Long id) {
        final ExpenseDto result =
                queryExpenseDto()
                        .leftJoin(expense.carStock, carStock)
                        .fetchJoin()
                        .where(notDeleted(expense), expense.id.eq(id))
                        .fetchFirst();
        return Optional.ofNullable(result);
    }

    @Override
    public Page<ExpenseDto> findAllPageByCondition(
            ExpenseFilterCondition condition, Pageable pageable) {
        final List<Long> coveringIndex =
                jpaQueryFactory
                        .select(expense.id)
                        .from(expense)
                        .where(
                                notDeleted(expense),
                                memberNameContainsOrNull(condition.getMemberName()),
                                departmentNameContainsOrNull(condition.getDepartmentName()),
                                carNameContainsOrNull(condition.getCarName()),
                                stockNumberContainsOrNull(condition.getStockNumber()),
                                descriptionContainsOrNull(condition.getDescription()),
                                dateBetween(
                                        expense.usedAt,
                                        condition.getStartDate(),
                                        condition.getEndDate()))
                        .fetch();

        final List<ExpenseDto> result =
                queryExpenseDto()
                        .leftJoin(expense.carStock, carStock)
                        .fetchJoin()
                        .orderBy(expense.usedAt.desc())
                        .where(expense.id.in(coveringIndex))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

        return PageableExecutionUtils.getPage(result, pageable, coveringIndex::size);
    }

    private JPAQuery<ExpenseDto> queryExpenseDto() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                ExpenseDto.class,
                                expense,
                                expense.member.name,
                                expense.updateMember.name,
                                expense.carStock.car.name,
                                expense.carStock.stockNumber,
                                expense.member.department.name))
                .from(expense);
    }

    private BooleanExpression memberNameContainsOrNull(String name) {
        return (name == null) ? null : expense.member.name.contains(name);
    }

    private BooleanExpression departmentNameContainsOrNull(String name) {
        return (name == null) ? null : expense.member.department.name.contains(name);
    }

    private BooleanExpression carNameContainsOrNull(String name) {
        return (name == null) ? null : expense.carStock.car.name.contains(name);
    }

    private BooleanExpression stockNumberContainsOrNull(String stockNumber) {
        return (stockNumber == null) ? null : expense.carStock.stockNumber.contains(stockNumber);
    }

    private BooleanExpression descriptionContainsOrNull(String description) {
        return (description == null) ? null : expense.description.contains(description);
    }

    private BooleanExpression dateBetween(DatePath<LocalDate> field, LocalDate from, LocalDate to) {
        return (from != null && to != null) ? field.isNotNull().and(field.between(from, to)) : null;
    }
}
