package ru.practicum.shareit.util;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.util.exception.BookingException;
import ru.practicum.shareit.util.exception.CommentException;
import ru.practicum.shareit.util.exception.ErrorResponse;
import ru.practicum.shareit.util.exception.NotFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler
    public ResponseEntity<?> handleThrowable(final MethodArgumentNotValidException e) {
        if (e.getMessage().contains("Данный email уже зарегестрирован")) {
            log.info("409 {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(e.getMessage()));
        }
        log.info("400 {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<?> handleThrowable(final NotFoundException e) {
        log.info("404 {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<?> handleThrowable(final CommentException e) {
        log.info("400 {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<?> handleThrowable(final BookingException e) {
        if (e.getMessage().contains("not available") || e.getMessage().contains("Booking can not be updated")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }
        log.info("404 {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
    }

//    @ExceptionHandler
//    public ResponseEntity<?> handleThrowable(final IllegalArgumentException e) {
//        log.info("400 {}", e.getMessage());
//        Map<String, String> errorResponse = new HashMap<>();
//        errorResponse.put("error", "Unknown state: " + e.getMessage().substring(e.getMessage()
//                .lastIndexOf(".") + 1));
//        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("Unknown state: " + e.getMessage().substring(e.getMessage()
//                .lastIndexOf(".") + 1)));
//    }

    @ExceptionHandler
    public ResponseEntity<?> handleThrowable(final IllegalArgumentException e) {
        log.info("400 {}", e.getMessage());
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Unknown state: " + e.getMessage().substring(e.getMessage()
                .lastIndexOf(".") + 1));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler
    public ResponseEntity<?> handleConstraintViolation(ConstraintViolationException e) {
        log.info("500 {}", e.getMessage());
        if (e.getMessage().contains("could not execute")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("Email already exists"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Constraint violation"));
    }
}