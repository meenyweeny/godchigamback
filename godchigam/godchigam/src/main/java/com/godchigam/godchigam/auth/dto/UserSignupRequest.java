package com.godchigam.godchigam.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class UserSignupRequest {

    String email;
    String password;
    String nickname;
}
