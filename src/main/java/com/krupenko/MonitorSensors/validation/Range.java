package com.krupenko.MonitorSensors.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = RangeValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Range {

    String message() default "From должен быть меньше To";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
