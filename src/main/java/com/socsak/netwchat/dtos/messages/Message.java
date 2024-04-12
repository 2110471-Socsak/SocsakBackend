package com.socsak.netwchat.dtos.messages;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Message {
    private String id;
    private String senderId;
    private Date sentAt;
    private String message;
}
