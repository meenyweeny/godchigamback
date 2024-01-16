package com.godchigam.godchigam.auth.service;

import com.godchigam.godchigam.auth.dto.UserSignupResponse;
import com.godchigam.godchigam.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserSignupResponse signupUser(User user)
}
