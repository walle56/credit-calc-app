package com.walle.credit.calc.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = StringToNumberValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface StringToNumberConstraint {
    String message() default "Invalid number format, supported formats '10' or '10.00'";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
