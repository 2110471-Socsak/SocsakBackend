package com.socsak.netwchat.dtos.messages;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MessageResponse {
    private String id;
    private String sender;
    private Date sentAt;
    private String message;
}
