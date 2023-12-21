package com.testcar.car.common.database;


import com.testcar.car.common.annotation.DistributedLock;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/** 분산락 어노테이션에 대한 AOP */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
@Order(Ordered.LOWEST_PRECEDENCE - 1)
public class DistributedLockAop {
    private final LockManager lockManager;

    @Around("@annotation(distributedLock)")
    public Object lock(ProceedingJoinPoint joinPoint, DistributedLock distributedLock) {
        final String key = createDynamicKey(joinPoint, distributedLock);
        return lockManager.lock(key, () -> proceed(joinPoint));
    }

    @SneakyThrows
    private Object proceed(ProceedingJoinPoint joinPoint) {
        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            log.error("Error during proceeding joinPoint", e);
            throw e;
        }
    }

    private String createDynamicKey(
            ProceedingJoinPoint joinPoint, DistributedLock distributedLock) {
        final String identifier = distributedLock.identifier();
        final DistributedLockType type = distributedLock.type();
        final Long id = findArgumentByName(joinPoint, identifier);
        return String.format("%s:%d", type.getLockName(), id);
    }

    public static <T> T findArgumentByName(JoinPoint joinPoint, String name) {
        try {
            final MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            final List<String> parameterNames = Arrays.asList(signature.getParameterNames());
            final List<Object> methodArguments = Arrays.asList(joinPoint.getArgs());

            final int parameterIndex = parameterNames.indexOf(name);
            return (T) methodArguments.get(parameterIndex);
        } catch (Exception e) {
            log.error("Reflection Error", e);
            throw new InvalidLockException("올바르지 않은 인수명입니다. " + name);
        }
    }
}
