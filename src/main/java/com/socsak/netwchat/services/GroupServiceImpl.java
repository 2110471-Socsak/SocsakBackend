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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
    public Group getGroupById(String groupId) {
        if (groupId == null) {
            throw new IllegalArgumentException("Group ID cannot be null");
        }

        Optional<Group> optionalGroup = groupRepository.findById(groupId);
        if (optionalGroup.isPresent()) {
            return optionalGroup.get();
        } else {
            return null;
        }
    }

    @Override
    public List<Group> getGroups() {
        return groupRepository.findAll();
    }
    
    @Override
    public List<Group> getGroupsByIdList(List<String> groupIds) {
        if (groupIds == null || groupIds.isEmpty()) {
            return Collections.emptyList();
        }
        return groupRepository.findAllById(groupIds);
    }
    

    @Override
    public GroupMsg sendMessage(String sender, String groupId, String message) throws Exception {
        Group group = groupRepository.findById(groupId).orElseThrow(GroupNotFoundException::new);

        User user = userRepository.findByUsername(sender).orElseThrow(UserNotFoundException::new);

        return groupMsgRepository.insert(new GroupMsg(
            user, group, message
        ));
    }

    @Override
    public List<GroupMsg> getMessages(String groupId, int page, int limit) {
        Page<GroupMsg> groupMsgPage = groupMsgRepository.findAllByGroupId(groupId, PageRequest.of(page, limit, Sort.by("sentAt").descending()));
        return groupMsgPage.getContent();
    }
}
