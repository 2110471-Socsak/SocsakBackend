package com.socsak.netwchat.dtos.messages;

import lombok.*;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SendGroupMessageRequest {
    private String message;
}
