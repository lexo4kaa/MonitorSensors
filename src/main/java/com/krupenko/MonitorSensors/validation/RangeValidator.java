package com.krupenko.MonitorSensors.validation;

import com.krupenko.MonitorSensors.dto.HasRange;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RangeValidator implements ConstraintValidator<Range, HasRange<? extends Number>> {

    @Override
    public boolean isValid(HasRange<? extends Number> object, ConstraintValidatorContext context) {
        Number fromValue = object.getFromValue();
        Number toValue = object.getToValue();
        if (fromValue == null || toValue == null) {
            return true; // поскольку данная валидация должна срабатывать только при вводе двух значений
        }
        return fromValue.doubleValue() < toValue.doubleValue();
    }

}
