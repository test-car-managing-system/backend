package com.testcar.car.common.entity;


import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.BooleanExpression;

public interface BaseQueryDslRepository {
    default BooleanExpression notDeleted(EntityPath<? extends BaseEntity> entityPath) {
        QBaseEntity qBaseEntity = new QBaseEntity(entityPath);
        return qBaseEntity.deleted.isFalse();
    }
}
