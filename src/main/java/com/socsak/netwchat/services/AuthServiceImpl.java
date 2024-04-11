package com.socsak.netwchat.services;

import com.socsak.netwchat.dtos.auth.AuthRequest;
import com.socsak.netwchat.exceptions.auth.DuplicateUsernameException;
import com.socsak.netwchat.models.User;
import com.socsak.netwchat.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails register(AuthRequest authRequest) throws Exception {
        try {
            String hashed = authRequest.getPassword();
            User user = userRepository.insert(new User(authRequest.getUsername(), hashed));
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .build();
        }
        catch (DuplicateKeyException e) {
            throw new DuplicateUsernameException();
        }
    }

    @Override
    public UserDetails login(AuthRequest authRequest) {
        User user = userRepository.findByUsername(authRequest.getUsername());
        if (user == null) {
            return null;
        }
        String hashedPasswordInput = authRequest.getPassword();
        if (user.getPassword().equals(hashedPasswordInput)) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .build();
        }
        return null;
    }
}
