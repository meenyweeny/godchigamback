package com.godchigam.godchigam.auth.service;

import com.godchigam.godchigam.auth.dto.UserLoginRequest;
import com.godchigam.godchigam.auth.dto.UserLoginResponse;
import com.godchigam.godchigam.auth.dto.UserSignupRequest;
import com.godchigam.godchigam.auth.dto.UserSignupResponse;
import com.godchigam.godchigam.auth.entity.User;
import com.godchigam.godchigam.auth.repository.UserRepository;
import com.godchigam.godchigam.global.jwt.JwtTokenProvider;
import com.godchigam.godchigam.global.jwt.JwtTokenProvider.TokenType;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.Token;
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
        String newJwt = jwtTokenProvider.generateToken(TokenType.Access, user.getUserId());
        System.out.println("new jwt is -> " + newJwt);
        return UserSignupResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    public UserLoginResponse login(UserLoginRequest userLoginRequest) throws Exception {
        User user = userRepository.findByEmail(userLoginRequest.getEmail());
        if(user == null) {
            throw new Exception("유저 없음ㅋ");
        }
        if(!user.getPassword().equals(userLoginRequest.getPassword())) {
            throw new Exception("비밀번호 다름ㅋ");
        }
        String accessToken = jwtTokenProvider.generateToken(TokenType.Access, user.getUserId());
        String refreshToken = jwtTokenProvider.generateToken(TokenType.Refresh, user.getUserId());
        return UserLoginResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
