package ru.practicum.shareit.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.util.exception.NotFoundException;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler
    public ResponseEntity<String> handleThrowable(final MethodArgumentNotValidException e) {
        if (e.getMessage().contains("Данный email уже зарегестрирован")) {
            log.info("409 {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
        log.info("400 {}", e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleThrowable(final NotFoundException e) {
        log.info("404 {}", e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
