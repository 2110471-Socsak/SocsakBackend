package com.socsak.netwchat.controllers;

import com.socsak.netwchat.dtos.generic.GenericResponse;
import com.socsak.netwchat.dtos.group.CreateGroupRequest;
import com.socsak.netwchat.dtos.messages.Message;
import com.socsak.netwchat.models.Group;
import com.socsak.netwchat.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("groups")
public class GroupController {

    @Autowired
    GroupService groupService;

    @PostMapping()
    public ResponseEntity<GenericResponse<Group>> createGroup(@RequestBody CreateGroupRequest createGroupRequest) {
       try {
           Group group = groupService.createGroup(createGroupRequest.getName());
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
    public ResponseEntity<GenericResponse<List<Message>>> getGroupMessagesHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "16") int limit,
            @PathVariable String groupId
    ) {
        ArrayList<Message> grRes = new ArrayList<>();
        for (int i=0; i<limit; i++) {
            Message message = Message.builder()
                    .id("Mongo ObjectID")
                    .senderId("Mongo ObjectID")
                    .message("text hello world welcome")
                    .sentAt(new Date(System.currentTimeMillis() - i * 2123L))
                    .build();
            grRes.add(message);
        }
        return ResponseEntity.ok(GenericResponse.success(grRes));
    }
}
