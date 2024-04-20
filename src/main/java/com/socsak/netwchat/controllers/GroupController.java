package com.socsak.netwchat.controllers;

import com.corundumstudio.socketio.SocketIOServer;
import com.socsak.netwchat.dtos.generic.GenericResponse;
import com.socsak.netwchat.dtos.group.CreateGroupRequest;
import com.socsak.netwchat.dtos.messages.MessageResponse;
import com.socsak.netwchat.dtos.messages.SendMessageRequest;
import com.socsak.netwchat.exceptions.group.GroupNotFoundException;
import com.socsak.netwchat.exceptions.user.UserNotFoundException;
import com.socsak.netwchat.models.Group;
import com.socsak.netwchat.models.GroupMsg;
import com.socsak.netwchat.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.socsak.netwchat.dtos.group.GroupResponse;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("groups")
public class GroupController {

    @Autowired
    GroupService groupService;
    @Autowired
    SocketIOServer ioServer;

    @GetMapping()
    public ResponseEntity<GenericResponse<List<GroupResponse>>> getAllGroups() {
        try {
            List<Group> groups = groupService.getGroups();

            List<GroupResponse> groupsResponse = groups.stream()
                    .map((Group group) -> {
                        String groupId = group.getId();
                        String groupName = group.getName();
                        int clientsCount = ioServer.getRoomOperations(groupId).getClients().size();

                        return GroupResponse.builder()
                                .id(groupId)
                                .name(groupName)
                                .count(clientsCount)
                                .build();
                    }).toList();

            return ResponseEntity.ok(GenericResponse.success(groupsResponse));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("{groupId}/messages")
    public ResponseEntity<GenericResponse<List<MessageResponse>>> getGroupMessagesHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "16") int limit,
            @PathVariable String groupId
    ) {
        List<GroupMsg> groupMsgList = groupService.getMessages(groupId, page, limit);
        return ResponseEntity.ok(GenericResponse.success(groupMsgList.stream().map(MessageResponse::new).toList()));
    }

}
