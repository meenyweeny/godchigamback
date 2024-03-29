package com.godchigam.godchigam.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserLoginResponse {

    Long userId;
    String email;
    String nickname;
    String accessToken;
    String refreshToken;
}
