package com.socsak.netwchat.repositories;

import com.socsak.netwchat.models.GroupMsg;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroupMsgRepository extends MongoRepository<GroupMsg, String> {
    Page<GroupMsg> findAllByGroupId(final String groupId, Pageable pageable);
}
