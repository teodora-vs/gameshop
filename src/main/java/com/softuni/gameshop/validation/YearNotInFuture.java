package com.softuni.gameshop.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = YearNotInFutureValidator.class)
public @interface YearNotInFuture {

    String message() default "year must not be in the future";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
