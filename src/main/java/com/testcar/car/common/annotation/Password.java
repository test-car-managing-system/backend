package com.testcar.car.common.annotation;


import com.testcar.car.common.annotation.validator.PasswordValidator;
import jakarta.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface Password {
    String message() default "8~20자 이내의 영문, 알파벳, 특수문자를 모두 포함하여 입력하세요.";

    Class[] groups() default {};

    Class[] payload() default {};
}
