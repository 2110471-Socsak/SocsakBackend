package com.socsak.netwchat.services;

import com.socsak.netwchat.models.PrivateMsg;

import java.util.List;

public interface PrivateService {
    PrivateMsg sendMessage(String senderId, String receiverId, String message);
    List<PrivateMsg> getMessages(String username1, String username2, int page, int limit);
}
