package com.socsak.netwchat.services;

import com.socsak.netwchat.dtos.auth.RegisterLoginRequest;
import com.socsak.netwchat.models.User;

public interface AuthService {
    User register(RegisterLoginRequest userRegisterLoginRequest) throws Exception;
}
