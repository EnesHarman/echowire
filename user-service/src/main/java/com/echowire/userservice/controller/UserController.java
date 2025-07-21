package com.echowire.userservice.controller;

import com.echowire.core.model.UserPreferences;
import com.echowire.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}/preferences")
    public ResponseEntity<UserPreferences> getUserPreferences(@PathVariable String id) {
        var preferences = userService.getUserPreferences(id);
        return ResponseEntity.ok(preferences);
    }
}
