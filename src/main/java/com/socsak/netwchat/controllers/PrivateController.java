package com.socsak.netwchat.controllers;

import com.socsak.netwchat.dtos.generic.GenericResponse;
import com.socsak.netwchat.dtos.messages.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("private")
public class PrivateController {

    @GetMapping("{userId}/messages")
    public ResponseEntity<GenericResponse<List<MessageResponse>>> getPrivateMessagesHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "16") int limit,
            @PathVariable String userId
    ) {
        List<MessageResponse> pvRes =new ArrayList<>();
        for (int i=0; i<limit; i++) {
            MessageResponse messageResponse = MessageResponse.builder()
                    .id("Mongo ObjectID")
                    .sender("username")
                    .message("text hello world welcome")
                    .sentAt(new Date(System.currentTimeMillis() - i * 2123L))
                    .build();
            pvRes.add(messageResponse);
        }
        return ResponseEntity.ok(GenericResponse.success(pvRes));
    }


}