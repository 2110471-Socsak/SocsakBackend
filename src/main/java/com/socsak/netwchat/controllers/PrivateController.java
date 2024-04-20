package com.socsak.netwchat.controllers;

import com.corundumstudio.socketio.SocketIOServer;
import com.socsak.netwchat.dtos.generic.GenericResponse;
import com.socsak.netwchat.dtos.messages.MessageResponse;
import com.socsak.netwchat.models.PrivateMsg;
import com.socsak.netwchat.services.AuthService;
import com.socsak.netwchat.services.PrivateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("private")
public class PrivateController {

    @Autowired
    AuthService authService;
    @Autowired
    PrivateService privateService;
    @Autowired
    SocketIOServer ioServer;

    @GetMapping("{username}/messages")
    public ResponseEntity<GenericResponse<List<MessageResponse>>> getPrivateMessagesHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "16") int limit,
            @PathVariable String username) {
        String reqUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        List<PrivateMsg> privateMsgList = privateService.getMessages(reqUsername, username, page, limit);

        return ResponseEntity.ok(GenericResponse.success(privateMsgList.stream().map(MessageResponse::new).toList()));
    }

}