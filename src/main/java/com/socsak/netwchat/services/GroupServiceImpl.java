package com.socsak.netwchat.services;

import com.socsak.netwchat.exceptions.group.GroupNotFoundException;
import com.socsak.netwchat.exceptions.user.UserNotFoundException;
import com.socsak.netwchat.models.Group;
import com.socsak.netwchat.models.GroupMsg;
import com.socsak.netwchat.models.User;
import com.socsak.netwchat.repositories.GroupMsgRepository;
import com.socsak.netwchat.repositories.GroupRepository;
import com.socsak.netwchat.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    GroupRepository groupRepository;
    @Autowired
    GroupMsgRepository groupMsgRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public Group createGroup(String name) {
        return groupRepository.insert(new Group(name));
    }

    @Override
    public List<Group> getGroups() {
        return groupRepository.findAll();
    }

    @Override
    public GroupMsg sendMessage(String sender, String groupId, String message) throws Exception {
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        Group group = groupOptional.orElseThrow(GroupNotFoundException::new);

        User user = userRepository.findByUsername(sender);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return groupMsgRepository.insert(new GroupMsg(
            user, group, message
        ));
    }

    @Override
    public List<GroupMsg> getMessages(String groupId) {
        return null;
    }
}
