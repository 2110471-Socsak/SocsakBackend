package com.socsak.netwchat.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterLoginRequest {

    private String username;
    private String password;

}
