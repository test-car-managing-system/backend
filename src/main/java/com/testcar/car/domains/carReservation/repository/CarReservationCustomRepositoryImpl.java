package com.testcar.car.domains.carReservation.repository;

import static com.testcar.car.domains.carReservation.entity.QCarReservation.carReservation;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.testcar.car.common.entity.BaseQueryDslRepository;
import com.testcar.car.domains.carReservation.entity.ReservationStatus;
import com.testcar.car.domains.carReservation.model.dto.CarReservationDto;
import com.testcar.car.domains.carReservation.model.vo.CarReservationFilterCondition;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class CarReservationCustomRepositoryImpl
        implements CarReservationCustomRepository, BaseQueryDslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<CarReservationDto> findAllPageByCondition(
            CarReservationFilterCondition condition, Pageable pageable) {
        final List<Long> coveringIndex =
                jpaQueryFactory
                        .select(carReservation.id)
                        .from(carReservation)
                        .where(
                                notDeleted(carReservation),
                                carNameContainsOrNull(condition.getName()),
                                reservationStatusEqOrNull(condition.getStatus()))
                        .fetch();

        final List<CarReservationDto> carReservations =
                jpaQueryFactory
                        .select(
                                Projections.constructor(
                                        CarReservationDto.class,
                                        carReservation,
                                        carReservation.carStock.car.name,
                                        carReservation.carStock.stockNumber))
                        .from(carReservation)
                        .where(carReservation.id.in(coveringIndex))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .orderBy(
                                getOrders(
                                        carReservation,
                                        pageable.getSort(),
                                        orderByReservationStatus(),
                                        carReservation.startedAt.desc()))
                        .fetch();
        return PageableExecutionUtils.getPage(carReservations, pageable, coveringIndex::size);
    }

    private BooleanExpression carNameContainsOrNull(String name) {
        return name == null ? null : carReservation.carStock.car.name.contains(name);
    }

    private BooleanExpression reservationStatusEqOrNull(ReservationStatus status) {
        return status == null ? null : carReservation.status.eq(status);
    }

    private OrderSpecifier<?> orderByReservationStatus() {
        return carReservation
                .status
                .when(ReservationStatus.RESERVED)
                .then(1)
                .when(ReservationStatus.RETURNED)
                .then(2)
                .otherwise(3)
                .asc();
    }
}
