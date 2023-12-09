package com.testcar.car.domains.car.repository;

import static com.testcar.car.domains.car.entity.QCar.car;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.testcar.car.common.entity.BaseQueryDslRepository;
import com.testcar.car.domains.car.entity.Car;
import com.testcar.car.domains.car.entity.Type;
import com.testcar.car.domains.car.model.vo.CarFilterCondition;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class CarCustomRepositoryImpl implements CarCustomRepository, BaseQueryDslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Car> findById(Long id) {
        final Car result =
                jpaQueryFactory.selectFrom(car).where(notDeleted(car), car.id.eq(id)).fetchFirst();
        return Optional.ofNullable(result);
    }

    @Override
    public Page<Car> findAllPageByCondition(CarFilterCondition condition, Pageable pageable) {
        final List<Long> coveringIndex =
                jpaQueryFactory
                        .select(car.id)
                        .from(car)
                        .where(
                                notDeleted(car),
                                carNameContainsOrNull(condition.getName()),
                                typeEqOrNull(condition.getType()),
                                createdAtBetween(condition.getStartDate(), condition.getEndDate()))
                        .fetch();

        final List<Car> cars =
                jpaQueryFactory
                        .selectFrom(car)
                        .where(car.id.in(coveringIndex))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .orderBy(
                                getOrders(
                                        car,
                                        pageable.getSort(),
                                        car.createdAt.desc(),
                                        car.name.asc()))
                        .fetch();
        return PageableExecutionUtils.getPage(cars, pageable, coveringIndex::size);
    }

    private BooleanExpression carNameContainsOrNull(String name) {
        return name == null ? null : car.name.contains(name);
    }

    private BooleanExpression typeEqOrNull(Type type) {
        return type == null ? null : car.type.eq(type);
    }

    private BooleanExpression createdAtBetween(LocalDate begin, LocalDate end) {
        return dateTimeBetween(car.createdAt, begin, end);
    }
}
