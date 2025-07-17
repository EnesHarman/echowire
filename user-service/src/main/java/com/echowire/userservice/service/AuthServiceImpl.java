package com.echowire.userservice.service;

import com.echowire.userservice.core.exception.LoginException;
import com.echowire.userservice.core.exception.RegisterException;
import com.echowire.userservice.dto.LoginRequest;
import com.echowire.userservice.dto.RegisterRequest;
import com.echowire.userservice.model.Role;
import com.echowire.userservice.model.User;
import com.echowire.userservice.repository.UserRepository;
import com.echowire.userservice.security.JwtProvider;
import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoWriteException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    private final JwtProvider jwtProvider;

    public AuthServiceImpl(UserRepository userRepository, BCryptPasswordEncoder encoder, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public void insertUser(RegisterRequest request) {
        try {
            var user = User.builder()
                    .email(request.email())
                    .passwordHash(encoder.encode(request.password()))
                    .preferredCategories(request.preferredCategories())
                    .preferredSources(request.preferredSources())
                    .roles(List.of(Role.USER))
                    .language(request.language())
                    .build();

            userRepository.save(user);
        } catch (DuplicateKeyException | MongoWriteException e) {
            throw new RegisterException("Error while registering. Please use a different email", HttpStatus.BAD_REQUEST, 1);
        }

    }

    @Override
    public String login(LoginRequest request) {
        var userOpt = userRepository.findByEmail(request.email());
        if (userOpt.isEmpty()) {
            throw new LoginException("Bad credentials, please check your information", HttpStatus.BAD_REQUEST, 2, request.email());
        }

        if (!encoder.matches(request.password(), userOpt.get().getPasswordHash())) {
            throw new LoginException("Bad credentials, please check your information", HttpStatus.BAD_REQUEST, 2, request.email());
        }

        return jwtProvider.generateToken(userOpt.get());
    }
}
