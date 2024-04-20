package com.socsak.netwchat.dtos.messages;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SendMessageRequest {
    private boolean group;
    private String room; // username or group_id
    private String message;
}
