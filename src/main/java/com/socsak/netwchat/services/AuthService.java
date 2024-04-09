package com.socsak.netwchat.services;

import com.socsak.netwchat.dtos.auth.RegisterLoginRequest;
import com.socsak.netwchat.models.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {
    UserDetails register(RegisterLoginRequest userRegisterLoginRequest) throws Exception;
    UserDetails login(RegisterLoginRequest userRegisterLoginRequest);
}
