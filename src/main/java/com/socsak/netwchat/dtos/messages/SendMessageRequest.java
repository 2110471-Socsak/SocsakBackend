package com.socsak.netwchat.dtos.messages;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SendMessageRequest {
    private String message;
}
