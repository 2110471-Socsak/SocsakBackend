package com.socsak.netwchat.services;

import com.socsak.netwchat.models.Group;
import com.socsak.netwchat.models.GroupMsg;

import java.util.List;

public interface GroupService {
    Group createGroup(String name);
    List<Group> getGroups();
    Group getGroupById(String groupId);
    List<Group> getGroupsByIdList(List<String> groupIds);
    GroupMsg sendMessage(String sender, String groupId, String message) throws Exception;
    List<GroupMsg> getMessages(String groupId, int page, int limit);
}
