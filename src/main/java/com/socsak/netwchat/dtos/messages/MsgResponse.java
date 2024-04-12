package com.socsak.netwchat.dtos.messages;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MsgResponse {

    private List<Message> messages;

}

