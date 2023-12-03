package com.testcar.car.common.response;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SuccessResponse<T> {
    private final String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    private final Boolean success = true;
    private int status;
    private final String message = "요청에 성공했습니다.";
    private T result;

    public static <T> SuccessResponse<T> from(T result) {
        return new SuccessResponse<>(200, result);
    }

    public static <T> SuccessResponse<T> from(int status, T result) {
        return new SuccessResponse<>(status, result);
    }
}
