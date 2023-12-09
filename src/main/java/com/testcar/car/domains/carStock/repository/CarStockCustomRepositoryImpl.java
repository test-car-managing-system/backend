package com.testcar.car.domains.carStock.repository;

import static com.testcar.car.domains.car.entity.QCar.car;
import static com.testcar.car.domains.carStock.entity.QCarStock.carStock;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.testcar.car.common.entity.BaseQueryDslRepository;
import com.testcar.car.domains.carStock.entity.CarStock;
import com.testcar.car.domains.carStock.entity.StockStatus;
import com.testcar.car.domains.carStock.model.vo.CarStockFilterCondition;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class CarStockCustomRepositoryImpl
        implements CarStockCustomRepository, BaseQueryDslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<CarStock> findById(Long id) {
        final CarStock result =
                jpaQueryFactory
                        .selectFrom(carStock)
                        .where(notDeleted(carStock), carStock.id.eq(id))
                        .fetchFirst();
        return Optional.ofNullable(result);
    }

    @Override
    public Page<CarStock> findAllPageByCondition(
            CarStockFilterCondition condition, Pageable pageable) {
        final List<Long> coveringIndex =
                jpaQueryFactory
                        .select(carStock.id)
                        .from(carStock)
                        .where(
                                notDeleted(carStock),
                                carIdEqOrNull(condition.getCarId()),
                                carNameContainsOrNull(condition.getName()),
                                stockNumberEqOrNull(condition.getStockNumber()),
                                stockStatusEqOrNull(condition.getStatus()))
                        .fetch();

        final List<CarStock> carStocks =
                jpaQueryFactory
                        .selectFrom(carStock)
                        .where(carStock.id.in(coveringIndex))
                        .leftJoin(carStock.car, car)
                        .fetchJoin()
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .orderBy(
                                getOrders(
                                        car,
                                        pageable.getSort(),
                                        orderByStockStatus(),
                                        car.name.asc()))
                        .fetch();
        return PageableExecutionUtils.getPage(carStocks, pageable, coveringIndex::size);
    }

    private BooleanExpression carIdEqOrNull(Long carId) {
        return carId == null ? null : carStock.car.id.eq(carId);
    }

    private BooleanExpression carNameContainsOrNull(String name) {
        return name == null ? null : car.name.contains(name);
    }

    private BooleanExpression stockNumberEqOrNull(String stockNumber) {
        return stockNumber == null ? null : carStock.stockNumber.eq(stockNumber);
    }

    private BooleanExpression stockStatusEqOrNull(StockStatus status) {
        return status == null ? null : carStock.status.eq(status);
    }

    private OrderSpecifier<?> orderByStockStatus() {
        return carStock.status
                .when(StockStatus.AVAILABLE)
                .then(1)
                .when(StockStatus.INSPECTION)
                .then(2)
                .when(StockStatus.UNAVAILABLE)
                .then(3)
                .when(StockStatus.RESERVED)
                .then(4)
                .otherwise(5)
                .asc();
    }
}
