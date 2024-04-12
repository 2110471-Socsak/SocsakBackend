package com.socsak.netwchat.controllers;

import com.socsak.netwchat.dtos.generic.GenericResponse;
import com.socsak.netwchat.dtos.messages.Message;
import com.socsak.netwchat.dtos.messages.MsgResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;

@RestController
@RequestMapping("private")
public class PrivateController {

    @GetMapping("{userId}/messages")
    public ResponseEntity<GenericResponse<MsgResponse>> getPrivateMessagesHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "16") int limit,
            @PathVariable String userId
    ) {
        MsgResponse pvRes = new MsgResponse(new ArrayList<>());
        for (int i=0; i<limit; i++) {
            Message message = Message.builder()
                    .id("Mongo ObjectID")
                    .senderId("Mongo ObjectID")
                    .message("text hello world welcome")
                    .sentAt(new Date(System.currentTimeMillis() - i * 2123L))
                    .build();
            pvRes.getMessages().add(message);
        }
        return ResponseEntity.ok(GenericResponse.success(pvRes));
    }


}