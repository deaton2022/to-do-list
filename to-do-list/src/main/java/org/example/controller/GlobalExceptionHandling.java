package org.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;


public class GlobalExceptionHandling {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleException(IllegalArgumentException ex) {

        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse(ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        // Log the exception?
        // most generic last!
        return new ResponseEntity<ErrorResponse>( new ErrorResponse("Something went wrong on our end :("), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
