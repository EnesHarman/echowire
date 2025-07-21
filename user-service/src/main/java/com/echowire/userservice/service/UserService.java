package com.echowire.userservice.service;

import com.echowire.core.model.UserPreferences;

public interface UserService {
    UserPreferences getUserPreferences(String id);
}
