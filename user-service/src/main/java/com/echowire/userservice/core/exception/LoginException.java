package com.echowire.userservice.core.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginException extends RuntimeException{
    private HttpStatus status;
    private int code;
    private String email;

    public LoginException(String message, HttpStatus status, int code, String email) {
        super(message);
        this.status = status;
        this.code = code;
        this.email = email;
    }
}
