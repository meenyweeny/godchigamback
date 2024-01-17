package com.godchigam.godchigam.auth.service;

import com.godchigam.godchigam.auth.dto.UserSignupRequest;
import com.godchigam.godchigam.auth.dto.UserSignupResponse;
import com.godchigam.godchigam.auth.entity.User;
import com.godchigam.godchigam.auth.repository.UserRepository;
import com.godchigam.godchigam.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public UserSignupResponse signupUser(UserSignupRequest userSignupRequest) {
        User user = User.builder()
                .email(userSignupRequest.getEmail())
                .password(userSignupRequest.getPassword())
                .nickname(userSignupRequest.getNickname())
                .build();
        user = userRepository.save(user);
        String newJwt = jwtTokenProvider.generateToken(user.getUserId());
        System.out.println("new jwt is -> " + newJwt);
        return UserSignupResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}
