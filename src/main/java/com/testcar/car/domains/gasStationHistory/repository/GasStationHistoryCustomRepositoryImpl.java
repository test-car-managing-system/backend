package com.testcar.car.domains.gasStationHistory.repository;

import static com.testcar.car.domains.gasStationHistory.entity.QGasStationHistory.gasStationHistory;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.testcar.car.common.entity.BaseQueryDslRepository;
import com.testcar.car.domains.gasStationHistory.model.dto.GasStationHistoryDto;
import com.testcar.car.domains.gasStationHistory.model.vo.GasStationHistoryFilterCondition;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class GasStationHistoryCustomRepositoryImpl
        implements GasStationHistoryCustomRepository, BaseQueryDslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<GasStationHistoryDto> findDetailById(Long id) {
        final GasStationHistoryDto result =
                queryGasStationHistoryDto()
                        .where(notDeleted(gasStationHistory), gasStationHistory.id.eq(id))
                        .fetchFirst();
        return Optional.ofNullable(result);
    }

    @Override
    public Page<GasStationHistoryDto> findAllPageByCondition(
            GasStationHistoryFilterCondition condition, Pageable pageable) {
        final List<Long> coveringIndex =
                jpaQueryFactory
                        .select(gasStationHistory.id)
                        .from(gasStationHistory)
                        .where(
                                notDeleted(gasStationHistory),
                                gasStationNameContainsOrNull(condition.getName()),
                                memberNameContainsOrNull(condition.getMemberName()),
                                stockNumberContainsOrNull(condition.getStockNumber()),
                                departmentNameContainsOrNull(condition.getDepartmentName()),
                                createdAtBetweenOrNull(
                                        condition.getStartDate(), condition.getEndDate()))
                        .fetch();

        final List<GasStationHistoryDto> result =
                queryGasStationHistoryDto()
                        .orderBy(gasStationHistory.createdAt.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

        return PageableExecutionUtils.getPage(result, pageable, coveringIndex::size);
    }

    private JPAQuery<GasStationHistoryDto> queryGasStationHistoryDto() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                GasStationHistoryDto.class,
                                gasStationHistory,
                                gasStationHistory.member.name,
                                gasStationHistory.carStock.car.name,
                                gasStationHistory.carStock.stockNumber,
                                gasStationHistory.member.department.name))
                .from(gasStationHistory);
    }

    private BooleanExpression gasStationNameContainsOrNull(String name) {
        return (name == null) ? null : gasStationHistory.gasStation.name.contains(name);
    }

    private BooleanExpression memberNameContainsOrNull(String name) {
        return (name == null) ? null : gasStationHistory.member.name.contains(name);
    }

    private BooleanExpression stockNumberContainsOrNull(String stockNumber) {
        return (stockNumber == null)
                ? null
                : gasStationHistory.carStock.stockNumber.contains(stockNumber);
    }

    private BooleanExpression departmentNameContainsOrNull(String name) {
        return (name == null) ? null : gasStationHistory.member.department.name.contains(name);
    }

    private BooleanExpression createdAtBetweenOrNull(LocalDate startDate, LocalDate endDate) {
        return dateTimeBetween(gasStationHistory.createdAt, startDate, endDate);
    }
}
