package com.socsak.netwchat.dtos.messages;

import com.socsak.netwchat.models.GroupMsg;
import com.socsak.netwchat.models.PrivateMsg;

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

    public MessageResponse(PrivateMsg privateMsg) {
        setId(privateMsg.getId());
        setSender(privateMsg.getSender().getUsername());
        setSentAt(privateMsg.getSentAt());
        setMessage(privateMsg.getMessage());
    }
}
