package com.socsak.netwchat.services;

import com.socsak.netwchat.models.PrivateMsg;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrivateServiceImpl implements PrivateService {
    @Override
    public List<PrivateMsg> getMessages(String userId, String otherUserId) {
        return null;
    }

    @Override
    public PrivateMsg sendMessage(String senderId, String receiverId, String message) {
        return null;
    }
}
