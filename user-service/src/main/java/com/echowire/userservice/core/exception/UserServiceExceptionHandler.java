package com.echowire.userservice.core.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserServiceExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(UserServiceExceptionHandler.class);

    @ExceptionHandler(RegisterException.class)
    public ResponseEntity<ExceptionResponse> handleException(RegisterException e) {
        var response = new ExceptionResponse(e.getCode(), e.getMessage());
        logger.error("Error: Same email has been used while registering");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ExceptionResponse> handleException(LoginException e) {
        var response = new ExceptionResponse(e.getCode(), e.getMessage());
        logger.error("Error: while logging in with email: " + e.getEmail());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ExceptionResponse> handleException(AuthorizationDeniedException e) {
        var response = new ExceptionResponse(32, "Error! You don't have the permission to access this route.");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(UserNotFoundException e) {
        var response = new ExceptionResponse(35, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception e) {
        var response = new ExceptionResponse(500, "Internal Server Error");
        logger.error("Error: " + e.getMessage(), e);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
