package com.socsak.netwchat.repositories;

import com.socsak.netwchat.models.GroupMsg;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroupMsgRepository extends MongoRepository<GroupMsg, String> {
}
