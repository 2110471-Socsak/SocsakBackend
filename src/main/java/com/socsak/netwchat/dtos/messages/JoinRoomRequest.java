package com.socsak.netwchat.dtos.messages;

import lombok.Getter;

@Getter
public class JoinRoomRequest {
    private boolean group;     
    private String room;    // username or group_id
}
