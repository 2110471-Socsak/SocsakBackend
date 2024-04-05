package com.socsak.netwchat.dtos.auth;

public class RegisterResponse {

    private String username;

    public RegisterResponse(String username) {
        setUsername(username);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
