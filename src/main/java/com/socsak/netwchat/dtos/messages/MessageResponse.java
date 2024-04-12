package com.socsak.netwchat.dtos.messages;

import com.socsak.netwchat.models.GroupMsg;
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

    public MessageResponse(GroupMsg groupMsg) {
        setId(groupMsg.getId());
        setSender(groupMsg.getSender().getUsername());
        setSentAt(groupMsg.getSentAt());
        setMessage(groupMsg.getMessage());
    }
}
