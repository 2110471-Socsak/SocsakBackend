package com.socsak.netwchat.repositories;

import com.socsak.netwchat.models.PrivateMsg;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface PrivateMsgRepository extends MongoRepository<PrivateMsg, String> {
    
    @Query("{$or: [ { 'sender.$username': ?0, 'receiver.$username': ?1 }, { 'sender.$username': ?1, 'receiver.$username': ?0 } ]}")
    Page<PrivateMsg> findMessagesByUsers(String username1, String username2, Pageable pageable);

}
