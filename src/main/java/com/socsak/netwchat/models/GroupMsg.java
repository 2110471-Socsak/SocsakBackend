package com.socsak.netwchat.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Document("group_msg")
public class GroupMsg {
    @Id
    private String id;
    @Setter
    @DBRef
    private User sender;
    @Setter
    @DBRef
    private Group group;
    @Setter
    private String message;
    @Setter
    private Date sentAt;

    public GroupMsg(User sender, Group group, String message) {
        super();
        this.sender = sender;
        this.group = group;
        this.message = message;
        this.sentAt = new Date();
    }
}
