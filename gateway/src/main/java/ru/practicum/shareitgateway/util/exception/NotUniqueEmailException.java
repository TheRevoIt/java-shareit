package ru.practicum.shareitgateway.util.exception;

class NotUniqueEmailException extends RuntimeException {
    public NotUniqueEmailException(String message) {
        super(message);
    }
}
