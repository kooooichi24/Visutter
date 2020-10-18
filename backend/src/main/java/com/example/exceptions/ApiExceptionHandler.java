package com.example.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import twitter4j.TwitterException;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {TwitterException.class})
    public ResponseEntity<Object> handleTwitterException(TwitterException e) {
        HttpStatus httpStatus = HttpStatus.valueOf(e.getStatusCode());

        // 1. Create payload containing exception details
        ApiException apiException = new ApiException(
                e.getMessage(),
                httpStatus,
                ZonedDateTime.now(ZoneId.of("Asia/Tokyo"))
        );

        // 2. Return response entity
        return new ResponseEntity<>(apiException, httpStatus);
    };
}
