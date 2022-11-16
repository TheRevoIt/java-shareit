package ru.practicum.shareit.util.exception;

import javax.validation.constraints.NotBlank;

public class NotUniqueEmailException extends RuntimeException {
    public NotUniqueEmailException(String message) {
       super(message);
    }
}
