package com.godchigam.godchigam.domain.auth.dto;

import lombok.Data;
import lombok.Getter;

@Data
public class UserLoginRequest {

    String email;
    String password;
}
