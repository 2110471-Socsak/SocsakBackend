package com.socsak.netwchat.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document("group")
public class Group {

    @Id
    private String id;
    @Setter
    private String name;

    public Group(String name) {
        super();
        setName(name);
    }

}
