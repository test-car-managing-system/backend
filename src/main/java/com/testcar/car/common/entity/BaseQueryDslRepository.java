package com.testcar.car.common.entity;


import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.PathBuilder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.data.domain.Sort;

public interface BaseQueryDslRepository {
    default BooleanExpression notDeleted(EntityPath<? extends BaseEntity> entityPath) {
        QBaseEntity qBaseEntity = new QBaseEntity(entityPath);
        return qBaseEntity.deleted.isFalse();
    }

    default BooleanExpression dateTimeBetween(
            DateTimePath<LocalDateTime> entityPath, LocalDate begin, LocalDate end) {
        BooleanExpression beginExpression =
                (begin == null) ? null : entityPath.goe(begin.atStartOfDay());
        BooleanExpression endExpression =
                (end == null) ? null : entityPath.lt(end.plusDays(1).atStartOfDay());
        return timeExpressionBetween(beginExpression, endExpression);
    }

    default BooleanExpression dateTimeBetween(
            DateTimePath<LocalDateTime> entityPath, LocalDateTime begin, LocalDateTime end) {
        BooleanExpression beginExpression = (begin != null) ? entityPath.goe(begin) : null;
        BooleanExpression endExpression = (end != null) ? entityPath.loe(end) : null;
        return timeExpressionBetween(beginExpression, endExpression);
    }

    private BooleanExpression timeExpressionBetween(
            BooleanExpression begin, BooleanExpression end) {
        if (begin != null && end != null) {
            return begin.and(end);
        } else if (begin != null) {
            return begin;
        }
        return end;
    }

    default OrderSpecifier<?>[] getOrders(EntityPath<?> qEntity, Sort sort) {
        return sort.stream().map(it -> getOrder(qEntity, it)).toArray(OrderSpecifier[]::new);
    }

    default OrderSpecifier<?>[] getOrders(
            EntityPath<?> qEntity, Sort sort, OrderSpecifier<?>... orderSpecifiers) {
        List<OrderSpecifier<?>> orderComparator = new ArrayList<>(Arrays.asList(orderSpecifiers));
        sort.stream().forEach(it -> orderComparator.add(getOrder(qEntity, it)));
        return orderComparator.toArray(OrderSpecifier[]::new);
    }

    @SuppressWarnings("all")
    private OrderSpecifier<?> getOrder(EntityPath<?> qEntity, Sort.Order order) {
        final Order direction = order.isAscending() ? Order.ASC : Order.DESC;
        final String property = order.getProperty();
        PathBuilder<?> pathBuilder =
                new PathBuilder<>(qEntity.getType(), qEntity.getMetadata().getName());
        return new OrderSpecifier(direction, pathBuilder.get(property));
    }
}
