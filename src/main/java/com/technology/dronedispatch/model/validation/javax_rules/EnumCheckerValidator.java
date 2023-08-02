package com.technology.dronedispatch.model.validation.javax_rules;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnumCheckerValidator implements ConstraintValidator<NotEnum, CharSequence> {

    private List<String> acceptedValues;

    @Override
    public void initialize(NotEnum constraintAnnotation) {
        acceptedValues = Stream.of(constraintAnnotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(CharSequence charSequence, ConstraintValidatorContext constraintValidatorContext) {
        if (charSequence == null){
            return false;
        }
        return acceptedValues.contains(charSequence.toString());
    }
}
