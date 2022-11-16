package ru.practicum.shareit.util.validation;

import org.springframework.beans.factory.annotation.Autowired;
import ru.practicum.shareit.user.UserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmailConstraint, String> {
    @Autowired
    UserRepository userRepository;

    @Override
    public void initialize(UniqueEmailConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return userRepository.uniqueEmailCheck(value);
    }
}
