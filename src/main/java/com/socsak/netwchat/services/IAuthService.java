package com.socsak.netwchat.services;

import com.socsak.netwchat.dtos.auth.RegisterLoginRequest;
import com.socsak.netwchat.models.User;

public interface IAuthService {

    User register(RegisterLoginRequest userRegisterLoginRequest);
}
