package com.socsak.netwchat.dtos.users;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class User {
    private String id;
    private String username;
    private boolean online;
}
