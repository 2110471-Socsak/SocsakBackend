package com.socsak.netwchat.controllers;

import com.socsak.netwchat.dtos.auth.RegisterLoginRequest;
import com.socsak.netwchat.dtos.auth.RegisterLoginResponse;
import com.socsak.netwchat.dtos.generic.GenericResponse;
import com.socsak.netwchat.exceptions.auth.DuplicateUsernameException;
import com.socsak.netwchat.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("auth")
public class AuthController {

    @Autowired
    AuthService authService;


    @PostMapping("register")
    public ResponseEntity<GenericResponse<RegisterLoginResponse>> register(@RequestBody RegisterLoginRequest usrl) {
        try {
            UserDetails user = authService.register(usrl);
            String token = "";
            return ResponseEntity.ok(GenericResponse.success(new RegisterLoginResponse(user.getUsername(), token)));
        } catch (DuplicateUsernameException e) {
            return ResponseEntity.badRequest().body(GenericResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("login")
    public ResponseEntity<GenericResponse<RegisterLoginResponse>> login(@RequestBody RegisterLoginRequest usrl) {
        try {
            UserDetails user = authService.login(usrl);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(GenericResponse.error("Invalid username or password"));
            }
            String token = "";
            return ResponseEntity.ok(GenericResponse.success(new RegisterLoginResponse(user.getUsername(), token)));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
