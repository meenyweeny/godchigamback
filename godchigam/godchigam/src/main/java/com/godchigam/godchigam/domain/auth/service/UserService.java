package com.godchigam.godchigam.domain.auth.service;

import com.godchigam.godchigam.domain.auth.dto.UserLoginRequest;
import com.godchigam.godchigam.domain.auth.dto.UserLoginResponse;
import com.godchigam.godchigam.domain.auth.dto.UserSignupRequest;
import com.godchigam.godchigam.domain.auth.dto.UserSignupResponse;
import com.godchigam.godchigam.domain.auth.entity.User;
import com.godchigam.godchigam.domain.auth.repository.UserRepository;
import com.godchigam.godchigam.global.auth.PasswordEncoder;
import com.godchigam.godchigam.global.jwt.JwtTokenProvider;
import com.godchigam.godchigam.global.jwt.JwtTokenProvider.TokenType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional(rollbackOn = {Exception.class})
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public UserSignupResponse signupUser(UserSignupRequest userSignupRequest) throws Exception {
        User user = User.builder()
                .email(userSignupRequest.getEmail())
                .password(userSignupRequest.getPassword())
                .nickname(userSignupRequest.getNickname())
                .build();
        User savedUser = userRepository.save(user);
        if(user.getEmail().equals("e@gmail.com")) {
            throw new Exception("일부러~");
        }
        updatePassword(savedUser.getUserId(), userSignupRequest.getPassword());
        return UserSignupResponse.builder()
                .userId(savedUser.getUserId())
                .email(savedUser.getEmail())
                .password(savedUser.getPassword())
                .nickname(savedUser.getNickname())
                .build();
    }

    public UserLoginResponse login(UserLoginRequest userLoginRequest) throws Exception {
        User user = userRepository.findByEmail(userLoginRequest.getEmail());
        if(user == null) {
            throw new Exception("유저 없음ㅋ");
        }
        if(!user.getPassword().equals(passwordEncoder.encrypt(user.getUserId(), userLoginRequest.getPassword()))) {
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

    public int updatePassword(Long userId, String password) {
        return userRepository.updatePassword(userId, passwordEncoder.encrypt(userId, password));
    }
}
