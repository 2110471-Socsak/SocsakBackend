package com.socsak.netwchat.services;

import com.mongodb.DuplicateKeyException;
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
    public User register(RegisterLoginRequest usrl) {
        try {
            return userRepository.insert(new User(usrl.getUsername(), usrl.getPassword()));
        }
        catch (Exception e) {
            return null;
        }
    }
}
