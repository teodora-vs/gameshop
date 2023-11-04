package com.softuni.gameshop.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Year;

public class YearNotInFutureValidator implements ConstraintValidator<YearNotInFuture, Number> {


    @Override
    public boolean isValid(Number value, ConstraintValidatorContext context) {
        if (value == null){
            return true;
        } else {
            int currentYear = Year.now().getValue();

            return value.intValue() <= currentYear;
        }
    }

}
