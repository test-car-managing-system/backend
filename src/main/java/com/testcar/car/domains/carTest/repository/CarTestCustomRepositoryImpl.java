package com.testcar.car.domains.carTest.repository;

import static com.testcar.car.domains.carTest.entity.QCarTest.carTest;
import static com.testcar.car.domains.gasStationHistory.entity.QGasStationHistory.gasStationHistory;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.testcar.car.common.entity.BaseQueryDslRepository;
import com.testcar.car.domains.carTest.model.vo.CarTestDto;
import com.testcar.car.domains.carTest.model.vo.CarTestFilterCondition;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class CarTestCustomRepositoryImpl
        implements CarTestCustomRepository, BaseQueryDslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<CarTestDto> findDetailById(Long id) {
        final CarTestDto result =
                queryCarTestDto().where(notDeleted(carTest), carTest.id.eq(id)).fetchFirst();
        return Optional.ofNullable(result);
    }

    @Override
    public Page<CarTestDto> findAllPageByCondition(
            CarTestFilterCondition condition, Pageable pageable) {
        final List<Long> coveringIndex =
                jpaQueryFactory
                        .select(carTest.id)
                        .from(carTest)
                        .where(
                                notDeleted(carTest),
                                trackNameContainsOrNull(condition.getTrackName()),
                                carNameContainsOrNull(condition.getCarName()),
                                memberNameContainsOrNull(condition.getMemberName()),
                                stockNumberContainsOrNull(condition.getStockNumber()),
                                departmentNameContainsOrNull(condition.getDepartmentName()),
                                performedAtBetweenOrNull(
                                        condition.getStartDate(), condition.getEndDate()))
                        .fetch();

        final List<CarTestDto> result =
                queryCarTestDto()
                        .orderBy(
                                getOrders(
                                        carTest,
                                        pageable.getSort(),
                                        carTest.performedAt.desc(),
                                        carTest.track.name.asc()))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

        return PageableExecutionUtils.getPage(result, pageable, coveringIndex::size);
    }

    private JPAQuery<CarTestDto> queryCarTestDto() {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                CarTestDto.class,
                                carTest,
                                carTest.track,
                                carTest.member.name,
                                gasStationHistory.member.department.name,
                                carTest.carStock.car.name,
                                carTest.carStock.stockNumber))
                .from(carTest);
    }

    private BooleanExpression trackNameContainsOrNull(String name) {
        return (name == null) ? null : carTest.track.name.contains(name);
    }

    private BooleanExpression carNameContainsOrNull(String name) {
        return (name == null) ? null : carTest.carStock.car.name.contains(name);
    }

    private BooleanExpression memberNameContainsOrNull(String name) {
        return (name == null) ? null : carTest.member.name.contains(name);
    }

    private BooleanExpression stockNumberContainsOrNull(String stockNumber) {
        return (stockNumber == null) ? null : carTest.carStock.stockNumber.contains(stockNumber);
    }

    private BooleanExpression departmentNameContainsOrNull(String name) {
        return (name == null) ? null : carTest.member.department.name.contains(name);
    }

    private BooleanExpression performedAtBetweenOrNull(LocalDate startDate, LocalDate endDate) {
        return dateTimeBetween(carTest.performedAt, startDate, endDate);
    }
}
