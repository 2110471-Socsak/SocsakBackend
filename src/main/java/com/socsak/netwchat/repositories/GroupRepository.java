package com.socsak.netwchat.repositories;

import com.socsak.netwchat.models.Group;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroupRepository extends MongoRepository<Group, String> {
}
