package com.socsak.netwchat.repositories;

import com.socsak.netwchat.models.Group;
import com.socsak.netwchat.models.GroupMsg;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroupRepository extends MongoRepository<Group, String> {
}
