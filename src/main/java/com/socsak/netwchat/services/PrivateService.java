package com.socsak.netwchat.services;

import com.socsak.netwchat.models.PrivateMsg;

import java.util.List;

public interface PrivateService {
    List<PrivateMsg> getMessages(String userId, String otherUserId);
    PrivateMsg sendMessage(String senderId, String receiverId, String message);
}
