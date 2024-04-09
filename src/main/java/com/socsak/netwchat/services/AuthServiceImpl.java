package com.socsak.netwchat.services;

import com.socsak.netwchat.dtos.auth.RegisterLoginRequest;
import com.socsak.netwchat.exceptions.auth.DuplicateUsernameException;
import com.socsak.netwchat.models.User;
import com.socsak.netwchat.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User register(RegisterLoginRequest usrl) throws Exception {
        try {
            return userRepository.insert(new User(usrl.getUsername()));
        }
        catch (DuplicateKeyException e) {
            throw new DuplicateUsernameException();
        }
    }

    @Override
    public User login(RegisterLoginRequest usrl) {
        return userRepository.findByUsername(usrl.getUsername());
    }
}
