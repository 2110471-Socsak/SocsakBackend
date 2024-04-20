package com.socsak.netwchat.services;

import com.socsak.netwchat.models.PrivateMsg;

import java.util.List;

public interface PrivateService {
    PrivateMsg sendMessage(String sender, String receiver, String message) throws Exception;
    List<PrivateMsg> getMessages(String username1, String username2, int page, int limit) throws Exception;
}
