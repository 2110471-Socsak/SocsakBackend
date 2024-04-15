package com.socsak.netwchat.dtos.messages;

import lombok.Getter;

@Getter
public class JoinRoomRequest {
    private char type;
    private String room;
}
