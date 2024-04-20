package com.socsak.netwchat.dtos.group;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GroupResponse {
    
    private String id;
    private String name;
    private int count;

}
