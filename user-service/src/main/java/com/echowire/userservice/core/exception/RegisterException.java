package com.echowire.userservice.core.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class RegisterException extends RuntimeException{
    private HttpStatus status;
    private int code;

    public RegisterException(String message, HttpStatus status, int code) {
        super(message);
        this.status = status;
        this.code = code;
    }
}
