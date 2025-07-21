package com.echowire.article.service;

import com.echowire.core.model.UserPreferences;

public interface UserServiceClient {
    UserPreferences getPreferences(String userId);
}
