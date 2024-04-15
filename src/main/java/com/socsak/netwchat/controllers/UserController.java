package com.socsak.netwchat.controllers;

import com.corundumstudio.socketio.SocketIOServer;
import com.socsak.netwchat.dtos.generic.GenericResponse;
import com.socsak.netwchat.dtos.users.UserExposed;
import com.socsak.netwchat.dtos.users.UsersResponse;
import com.socsak.netwchat.models.User;
import com.socsak.netwchat.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    SocketIOServer ioServer;

    @GetMapping()
    public ResponseEntity<GenericResponse<UsersResponse>> getAllUsers() {
        Set<String> connectedUsers = new HashSet<>(
                ioServer.getAllClients().stream().map(
                        client -> ((UserDetails) client.get("user")).getUsername()
                ).toList()
        );

        List<User> users = userService.getAllUsers();
        UsersResponse usersResponse = new UsersResponse(
                users.stream().map((user) ->
                        UserExposed.builder()
                        .username(user.getUsername())
                        .online(connectedUsers.contains(user.getUsername()))
                        .build()).toList()
        );

        return ResponseEntity.ok(GenericResponse.success(usersResponse));
    }
}
