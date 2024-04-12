package com.socsak.netwchat.services;

import com.socsak.netwchat.models.Group;
import com.socsak.netwchat.models.GroupMsg;

import java.util.List;

public interface GroupService {
    Group createGroup(String name);
    List<Group> getGroups();
    GroupMsg sendMessage(String senderId, String GroupId, String message);
    List<GroupMsg> getMessages(String groupId);
}
