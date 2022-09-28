package com.travelers.annotation.validator;

import com.travelers.annotation.Tel;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TelValidator implements ConstraintValidator<Tel, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null) return false;
        return value.matches("(01[016789])(\\d{3,4})(\\d{4})");
    }
}
