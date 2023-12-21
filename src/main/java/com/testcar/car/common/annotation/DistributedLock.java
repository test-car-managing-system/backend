package com.testcar.car.common.annotation;


import com.testcar.car.common.database.DistributedLockType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 분산 락을 걸기 위한 어노테이션 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DistributedLock {
    DistributedLockType type();

    String identifier();
}
