package com.echowire.article.service;

import com.echowire.core.model.UserPreferences;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class UserServiceClientImpl implements UserServiceClient {

    private final RestClient restClient;

    public UserServiceClientImpl() {
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:8091") //TODO eureka will replace this dude.
                .build();
    }

    @Override
    public UserPreferences getPreferences(String userId) {
        return restClient.get()
                .uri("v1/user/{id}/preferences", userId)
                .header("X-Gateway-Authorized", "true")
                .retrieve()
                .body(UserPreferences.class);
    }
}
