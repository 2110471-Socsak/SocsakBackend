package com.socsak.netwchat.controllers;

import com.socsak.netwchat.dtos.generic.GenericResponse;
import com.socsak.netwchat.dtos.users.User;
import com.socsak.netwchat.dtos.users.UsersResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("users")
public class UserController {

    @GetMapping()
    public ResponseEntity<GenericResponse<UsersResponse>> getAllUsers() {
        UsersResponse usersResponse = new UsersResponse(new ArrayList<>());
        for (int i=0; i<4; i++) {
            User user = new User("Mongo ObjectID", "username" + i, i%2 == 0);
            usersResponse.getUsers().add(user);
        }
        return ResponseEntity.ok(GenericResponse.success(usersResponse));
    }
}
