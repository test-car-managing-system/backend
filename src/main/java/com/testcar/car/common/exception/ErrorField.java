package com.testcar.car.common.exception;


import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

/** method argument exception 의 필드 오류를 설명하는 클래스 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorField {
    private String field;

    private String value;

    private String reason;

    public static List<ErrorField> from(BindingResult bindingResult) {
        return bindingResult.getAllErrors().stream().map(ErrorField::from).toList();
    }

    private static ErrorField from(ObjectError objectError) {
        final FieldError fieldError = (FieldError) objectError;
        return new ErrorField(
                fieldError.getField(),
                Objects.toString(fieldError.getRejectedValue()),
                fieldError.getDefaultMessage());
    }
}
