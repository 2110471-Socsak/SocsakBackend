package com.socsak.netwchat.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("user")
public class User {

    @Id
    private String id;
    private String username;
    private String password;

    public User(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

}
