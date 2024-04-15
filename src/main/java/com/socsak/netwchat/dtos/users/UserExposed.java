package com.socsak.netwchat.dtos.users;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserExposed {
    private String username;
    private boolean online;
}
