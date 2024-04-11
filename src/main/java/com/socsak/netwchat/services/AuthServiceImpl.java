package com.socsak.netwchat.services;

import com.socsak.netwchat.dtos.auth.AuthRequest;
import com.socsak.netwchat.exceptions.auth.DuplicateUsernameException;
import com.socsak.netwchat.models.User;
import com.socsak.netwchat.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public UserDetails register(AuthRequest authRequest) throws Exception {
        try {
            String hashed = passwordEncoder.encode(authRequest.getPassword());
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
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );
        User user = userRepository.findByUsername(authRequest.getUsername());
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }
}
