package com.socsak.netwchat.dtos.messages;

import lombok.Getter;

@Getter
public class JoinRoomRequest {
    private char type;      // P or G
    private String room;    // username or group_id
}
