package com.socsak.netwchat.dtos.users;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UsersResponse {

    List<UserExposed> users;
}
