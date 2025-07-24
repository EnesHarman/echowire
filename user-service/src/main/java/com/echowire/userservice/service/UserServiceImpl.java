package com.echowire.userservice.service;

import com.echowire.user_preference.UserPreferencesRequest;
import com.echowire.userservice.core.exception.UserNotFoundException;
import com.echowire.userservice.model.User;
import com.echowire.userservice.repository.UserRepository;
import io.grpc.stub.StreamObserver;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.echowire.user_preference.UserPreferenceServiceGrpc;
import com.echowire.user_preference.UserPreferences;
import java.util.Optional;

@Service
public class UserServiceImpl extends UserPreferenceServiceGrpc.UserPreferenceServiceImplBase implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void getUserPreferences(UserPreferencesRequest request, StreamObserver<UserPreferences> responseObserver) {
        Optional<User> userOpt = userRepository.findById(request.getId());
        if (userOpt.isEmpty()) {
            throw new UserNotFoundException("There is no such a user with given credentials.", HttpStatus.BAD_REQUEST, 315);
        }
        var user = userOpt.get();
        var preferences = UserPreferences.newBuilder()
                .addAllCategories(user.getPreferredCategories())
                .build();
        responseObserver.onNext(preferences);
        responseObserver.onCompleted();
    }
}
