package com.socsak.netwchat.services;

import com.socsak.netwchat.dtos.auth.RegisterLoginRequest;
import com.socsak.netwchat.models.User;
import com.socsak.netwchat.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User register(RegisterLoginRequest userRegisterLoginRequest) {
        return userRepository.insert(new User(userRegisterLoginRequest.getUsername(), userRegisterLoginRequest.getPassword()));
    }
}
