package com.echowire.userservice.core.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class UserNotFoundException extends RuntimeException {
    private HttpStatus status;
    private int code;

    public UserNotFoundException(String message, HttpStatus status, int code) {
        super(message);
        this.status = status;
        this.code = code;
    }
}
