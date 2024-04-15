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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("groups")
public class GroupController {

    @Autowired
    GroupService groupService;

    @Autowired
    SocketIOServer ioServer;

    @PostMapping()
    public ResponseEntity<GenericResponse<Group>> createGroup(@RequestBody CreateGroupRequest createGroupRequest) {
       try {
           Group group = groupService.createGroup(createGroupRequest.getName());
           ioServer.getBroadcastOperations().sendEvent("group_created", group);
           return ResponseEntity.status(HttpStatus.CREATED).body(GenericResponse.success(group));
       } catch (Exception e) {
           return ResponseEntity.internalServerError().build();
       }
    }

    @GetMapping()
    public ResponseEntity<GenericResponse<List<Group>>> getAllGroups() {
        try {
            List<Group> groups = groupService.getGroups();
            return ResponseEntity.ok(GenericResponse.success(groups));
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


    // DEV
    @PostMapping("{groupId}/send")
    public ResponseEntity<GenericResponse<MessageResponse>> sendMessage(
            @RequestBody SendMessageRequest request,
            @PathVariable String groupId
    ) {
        try {
            String sender = SecurityContextHolder.getContext().getAuthentication().getName();
            GroupMsg groupMsg = groupService.sendMessage(sender, groupId, request.getMessage());
            return ResponseEntity.ok(GenericResponse.success(MessageResponse.builder()
                            .id(groupMsg.getId())
                            .sender(groupMsg.getSender().getUsername())
                            .message(groupMsg.getMessage())
                            .sentAt(groupMsg.getSentAt())
                    .build()));
        } catch (GroupNotFoundException | UserNotFoundException e) {
            return ResponseEntity.badRequest().body(GenericResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(GenericResponse.error(e.getMessage()));
        }
    }
}
