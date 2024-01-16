package com.godchigam.godchigam.auth.service;

import com.godchigam.godchigam.auth.dto.UserSignupRequest;
import com.godchigam.godchigam.auth.dto.UserSignupResponse;
import com.godchigam.godchigam.auth.entity.User;
import com.godchigam.godchigam.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserSignupResponse signupUser(UserSignupRequest userSignupRequest) {
        User user = User.builder()
                .email(userSignupRequest.getEmail())
                .password(userSignupRequest.getPassword())
                .nickname(userSignupRequest.getNickname())
                .build();
        user = userRepository.save(user);
        return UserSignupResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}
