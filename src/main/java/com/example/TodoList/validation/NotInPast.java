package com.example.TodoList.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NotInPastValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotInPast {
    String message() default "{com.example.TodoList.validation.NotInPast.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
