package ru.practicum.shareitgateway.booking.validation;

import ru.practicum.shareitgateway.booking.dto.BookingDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

class EndDateAfterStartDateValidator implements ConstraintValidator<EndDateAfterStartDate, BookingDto> {
    @Override
    public void initialize(EndDateAfterStartDate constraintAnnotation) {
    }

    @Override
    public boolean isValid(BookingDto booking, ConstraintValidatorContext context) {
        LocalDateTime start = booking.getStart();
        LocalDateTime end = booking.getEnd();
        if (start == null || end == null) {
            return false;
        }
        return booking.getEnd().isAfter(booking.getStart());
    }
}