package com.socsak.netwchat.services;

import com.socsak.netwchat.exceptions.user.UserNotFoundException;
import com.socsak.netwchat.models.PrivateMsg;
import com.socsak.netwchat.models.User;
import com.socsak.netwchat.repositories.PrivateMsgRepository;
import com.socsak.netwchat.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrivateServiceImpl implements PrivateService {

    @Autowired
    PrivateMsgRepository privateMsgRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public List<PrivateMsg> getMessages(String username1, String username2, int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<PrivateMsg> privateMsgPage = privateMsgRepository.findMessagesByUsers(username1, username2, pageable);
        return privateMsgPage.getContent();  
    }

    @Override
    public PrivateMsg sendMessage(String sender, String receiver, String message) throws Exception {
        User senderUser = userRepository.findByUsername(sender).orElseThrow(UserNotFoundException::new);
        User receiverUser = userRepository.findByUsername(receiver).orElseThrow(UserNotFoundException::new);

        return privateMsgRepository.insert(new PrivateMsg(
            senderUser, receiverUser, message
        ));
    }
}
