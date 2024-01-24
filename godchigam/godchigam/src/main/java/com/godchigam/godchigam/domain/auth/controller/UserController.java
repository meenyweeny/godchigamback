package com.godchigam.godchigam.domain.auth.controller;

import com.godchigam.godchigam.domain.auth.dto.UserLoginRequest;
import com.godchigam.godchigam.domain.auth.dto.UserLoginResponse;
import com.godchigam.godchigam.domain.auth.dto.UserNicknameResponse;
import com.godchigam.godchigam.domain.auth.dto.UserSignupRequest;
import com.godchigam.godchigam.domain.auth.dto.UserSignupResponse;
import com.godchigam.godchigam.domain.auth.entity.User;
import com.godchigam.godchigam.domain.auth.service.UserService;
import com.godchigam.godchigam.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<UserSignupResponse> signUpUser(@RequestBody UserSignupRequest userSignupRequest) throws Exception {
        return ResponseEntity.ok(userService.signupUser(userSignupRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest) throws Exception {
        return ResponseEntity.ok(userService.login(userLoginRequest));
    }

    @GetMapping("/nickname")
    public ResponseEntity<UserNicknameResponse> findNickname(@AuthenticationPrincipal User user) throws Exception {
        System.out.println("CONTROLLER USER INFO " + user);
        return ResponseEntity.ok(userService.findNickname(user.getEmail()));
    }
}
