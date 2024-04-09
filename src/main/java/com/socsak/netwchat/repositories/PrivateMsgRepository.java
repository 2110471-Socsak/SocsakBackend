package com.socsak.netwchat.repositories;

import com.socsak.netwchat.models.PrivateMsg;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PrivateMsgRepository extends MongoRepository<PrivateMsg, String> {
}
