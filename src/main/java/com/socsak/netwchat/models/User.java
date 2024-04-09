package com.socsak.netwchat.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("user")
public class User {

    @Id
    private String id;
    @Getter
    @Setter
    @Indexed(unique = true)
    private String username;

    public User(String username) {
        super();
        this.username = username;
    }
}
