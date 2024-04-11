package com.socsak.netwchat.services;

import com.socsak.netwchat.dtos.auth.AuthRequest;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {
    UserDetails register(AuthRequest userRegisterLoginRequest) throws Exception;
    UserDetails login(AuthRequest userRegisterLoginRequest);
}
