package com.walle.credit.calc.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validates if string can be converted to number, allowed characters are digits and 'dot'
 */
public class StringToNumberValidator implements ConstraintValidator<StringToNumberConstraint, String> {

    private static final String NUMBER_REGEX = "\\d+(\\.\\d+)?";

    @Override
    public void initialize(StringToNumberConstraint constraint) {
    }

    @Override
    public boolean isValid(String string, ConstraintValidatorContext cvc) {
        return string.matches(NUMBER_REGEX);
    }
}
