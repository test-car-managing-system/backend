package com.testcar.car.common.database;

public class InvalidLockException extends RuntimeException {
    public InvalidLockException(String message) {
        super(message);
    }

    public InvalidLockException(String message, Throwable cause) {
        super(message, cause);
    }
}
