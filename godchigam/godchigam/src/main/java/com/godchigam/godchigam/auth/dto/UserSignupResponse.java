package com.godchigam.godchigam.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSignupResponse {

    private Long userId;
    private String email;
    private String password;
    private String nickname;
}
