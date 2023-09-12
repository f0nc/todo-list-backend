package com.example.TodoList.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Instant;

public class NotInPastValidator implements ConstraintValidator<NotInPast, Instant> {
    @Override
    public boolean isValid(Instant value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return !value.isBefore(Instant.now());
    }
}
