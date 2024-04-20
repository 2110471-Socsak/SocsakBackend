package com.socsak.netwchat.repositories;

import com.socsak.netwchat.models.PrivateMsg;
import com.socsak.netwchat.models.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PrivateMsgRepository extends MongoRepository<PrivateMsg, String> {
    
    Page<PrivateMsg> findBySenderAndReceiverOrSenderAndReceiver(
        User sender1, User receiver1, 
        User sender2, User receiver2, 
        Pageable pageable
    );

}
