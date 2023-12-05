package com.testcar.car.common.annotation;


import com.testcar.car.domains.member.Role;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** api 사용에 필요한 권한을 지정하는 어노테이션 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RoleAllowed {
    Role role() default Role.USER;
}
