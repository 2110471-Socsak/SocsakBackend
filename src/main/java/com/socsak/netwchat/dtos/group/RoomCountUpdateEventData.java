package com.socsak.netwchat.dtos.group;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class RoomCountUpdateEventData {

    private String groupId;
    private int count;
}
