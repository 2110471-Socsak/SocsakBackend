package com.socsak.netwchat.services;

import com.socsak.netwchat.models.Group;
import com.socsak.netwchat.models.GroupMsg;
import com.socsak.netwchat.repositories.GroupMsgRepository;
import com.socsak.netwchat.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    GroupRepository groupRepository;
    @Autowired
    GroupMsgRepository groupMsgRepository;

    @Override
    public Group createGroup(String name) {
        return groupRepository.insert(new Group(name));
    }

    @Override
    public List<Group> getGroups() {
        return groupRepository.findAll();
    }

    @Override
    public GroupMsg sendMessage(String senderId, String GroupId, String message) {
        return null;
    }

    @Override
    public List<GroupMsg> getMessages(String groupId) {
        return null;
    }
}
