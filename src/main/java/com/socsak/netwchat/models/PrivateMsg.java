package com.socsak.netwchat.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Document("private_msg")
public class PrivateMsg {
    @Id
    private String id;
    @Setter
    @DBRef
    private User sender;
    @Setter
    @DBRef
    private User receiver;
    @Setter
    private String message;
    @Setter
    private Date sentAt;

    public PrivateMsg(User sender, User receiver, String message) {
        super();
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.sentAt = new Date();
    }
}