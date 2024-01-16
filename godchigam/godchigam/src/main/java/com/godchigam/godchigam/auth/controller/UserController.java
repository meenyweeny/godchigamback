package com.godchigam.godchigam.auth.controller;

import com.godchigam.godchigam.auth.dto.UserSignupRequest;
import com.godchigam.godchigam.auth.dto.UserSignupResponse;
import com.godchigam.godchigam.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserSignupResponse> signUpUser(@RequestBody UserSignupRequest userSignupRequest) {
        return ResponseEntity.ok(userService.signupUser(userSignupRequest));
    }
}
