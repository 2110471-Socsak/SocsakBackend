package com.socsak.netwchat.controllers;

import com.socsak.netwchat.dtos.auth.RegisterLoginRequest;
import com.socsak.netwchat.dtos.auth.RegisterResponse;
import com.socsak.netwchat.dtos.generic.GenericResponse;
import com.socsak.netwchat.models.User;
import com.socsak.netwchat.services.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("auth")
public class AuthController {

    @Autowired
    IAuthService authService;

    @PostMapping("register")
    public ResponseEntity<GenericResponse<RegisterResponse>> register(@RequestBody RegisterLoginRequest uslr) {
        User user = authService.register(uslr);
        if (user == null) {
            return ResponseEntity.badRequest().body(GenericResponse.error());
        }
        return ResponseEntity.ok(GenericResponse.success(new RegisterResponse(user.getUsername())));
    }
}
