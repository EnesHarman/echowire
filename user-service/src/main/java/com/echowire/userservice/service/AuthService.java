package com.echowire.userservice.service;

import com.echowire.userservice.dto.LoginRequest;
import com.echowire.userservice.dto.RegisterRequest;

public interface AuthService {
    void insertUser(RegisterRequest request);

    String login(LoginRequest request);
}
