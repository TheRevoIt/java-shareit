package ru.practicum.shareit.util.validation;

import org.springframework.beans.factory.annotation.Autowired;
import ru.practicum.shareit.user.UserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

class UniqueEmailValidator implements ConstraintValidator<UniqueEmailConstraint, String> {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(UniqueEmailConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Objects.isNull(value)) {
            return true;
        } else if (!value.isBlank()) {
            return userRepository.uniqueEmailCheck(value);
        } else {
            return false;
        }
    }
}
