package com.echowire.userservice.service;

import com.echowire.core.model.UserPreferences;
import com.echowire.userservice.core.exception.UserNotFoundException;
import com.echowire.userservice.model.User;
import com.echowire.userservice.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserPreferences getUserPreferences(String id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            throw new UserNotFoundException("There is no such a user with given credentials.", HttpStatus.BAD_REQUEST, 315);
        }
        var user = userOpt.get();

        return new UserPreferences(user.getPreferredCategories(), user.getPreferredSources(), user.getLanguage());
    }
}
