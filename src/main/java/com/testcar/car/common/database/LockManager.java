package com.testcar.car.common.database;


import java.util.function.Supplier;

/** 분산 락 관련 메소드 인터페이스 */
public interface LockManager {
    <T> T lock(String lockName, Supplier<T> runnable);
}
