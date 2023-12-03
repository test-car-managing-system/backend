package com.testcar.car.common.response;


import com.testcar.car.common.exception.BaseException;
import com.testcar.car.common.exception.ErrorField;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;

@Slf4j
@Getter
@Builder
public class ErrorResponse {
    private final String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    private final boolean success = false;
    private int status;
    private String message;
    private List<ErrorField> errors;
    private String code;

    public static ErrorResponse from(BaseException exception) {
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .status(exception.getHttpStatus().value())
                .errors(List.of())
                .code(exception.getCode())
                .build();
    }

    public static ErrorResponse of(BaseException exception, BindingResult bindingResult) {
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .status(exception.getHttpStatus().value())
                .errors(ErrorField.from(bindingResult))
                .code(exception.getCode())
                .build();
    }
}
