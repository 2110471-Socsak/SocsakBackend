package com.socsak.netwchat.controllers;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.socsak.netwchat.dtos.group.CreateGroupRequest;
import com.socsak.netwchat.dtos.messages.JoinRoomRequest;
import com.socsak.netwchat.dtos.messages.MessageResponse;
import com.socsak.netwchat.dtos.messages.SendMessageRequest;
import com.socsak.netwchat.models.Group;
import com.socsak.netwchat.models.GroupMsg;
import com.socsak.netwchat.models.PrivateMsg;
import com.socsak.netwchat.services.GroupService;
import com.socsak.netwchat.services.PrivateService;
import com.socsak.netwchat.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SocketController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDetailsService userDetailService;
    @Autowired
    private PrivateService privateService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private SocketIOServer server;


    public SocketController(SocketIOServer server) {
        server.addConnectListener(onConnected());
        server.addDisconnectListener(onDisconnected());
        server.addEventListener("join_room", JoinRoomRequest.class, onJoinRequest());
        server.addEventListener("create_group", CreateGroupRequest.class, onCreateGroupRequest());
        server.addEventListener("send_message", SendMessageRequest.class, onMessageReceived());
    }

    private ConnectListener onConnected() {
        return (client) -> {
            System.out.println("Socket ID [ " + client.getSessionId().toString() +" ] connected to socket");

            //  1. Token check and extract username
            UserDetails userDetails = authorize(client);
            if (userDetails == null) {
                System.out.println("JWT Token is invalid");
                client.disconnect();
                return;
            }
            client.set("user", userDetails);
            System.out.println("User " + ((UserDetails) client.get("user")).getUsername() + " connected");

            server.getBroadcastOperations().sendEvent("user_connected", userDetails.getUsername());
        };
    }

    private UserDetails authorize(SocketIOClient client) {
        String authHeader = client.getHandshakeData().getHttpHeaders().get("Authorization");
        final String jwt;
        String username;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }

        jwt = authHeader.substring(7);
        username = jwtUtil.extractUsername(jwt);
        if (username == null) {
            return null;
        }

        UserDetails userDetails = userDetailService.loadUserByUsername(username);
        if (!jwtUtil.isValidToken(jwt, userDetails)) {
            return null;
        }

        return userDetails;
    }

    private DisconnectListener onDisconnected() {
        return (client) -> {
            System.out.println("Client [ " + client.getSessionId().toString() + " ] - Disconnected from socket");

            String username;
            try {
                UserDetails userDetails = client.get("user");
                username = userDetails.getUsername();
            } catch (Exception e) {
                username = null;
            }
            server.getBroadcastOperations().sendEvent("user_disconnected", username);

        };
    }

    private DataListener<JoinRoomRequest> onJoinRequest() {
        return (senderClient, data, ackSender) -> {

            try {
                UserDetails userDetails = senderClient.get("user");
                String username = userDetails.getUsername();
                ArrayList<String> currentRooms = new ArrayList<String>(senderClient.getAllRooms());
                String joinRoom = data.getRoom();

                if (!currentRooms.isEmpty()) {
                    if (currentRooms.contains(joinRoom)) {
                        return; // User is already in the requested room, no need to leave and rejoin
                    }
                    senderClient.leaveRooms(senderClient.getAllRooms());
                    List<Group> currentGroupRooms = groupService.getGroupsByIdList(currentRooms);
                    if (!currentGroupRooms.isEmpty()) {
                        server.getBroadcastOperations().sendEvent("left_room", currentGroupRooms);
                    }
                }

                if (data.isGroup()) {
                    senderClient.joinRoom(joinRoom);
                    Group joinGroupRoom = groupService.getGroupById(joinRoom);
                    server.getBroadcastOperations().sendEvent("joined_room", joinGroupRoom);
                } else {
                    senderClient.joinRoom(username + "_" + joinRoom);
                }
            } catch (Exception e) {
                System.err.println("Error processing join request: " + e.getMessage());
            }
        };
    };

    private DataListener<CreateGroupRequest> onCreateGroupRequest() {
        return (senderClient, data, ackSender) -> {
            Group group = groupService.createGroup(data.getName());
            System.out.println("Group created [ " + group.getName() + " ']");
            server.getBroadcastOperations().sendEvent("group_created", group);
        };
    }

    private DataListener<SendMessageRequest> onMessageReceived() {
        return (senderClient, data, ackSender) -> {
            try {
                UserDetails userDetails = senderClient.get("user");
                String username = userDetails.getUsername();

                if (data.isGroup()) {
                    GroupMsg sentMessage = groupService.sendMessage(username, data.getRoom(), data.getMessage());
                    server.getRoomOperations(data.getRoom()).sendEvent("new_message", new MessageResponse(sentMessage));
                } else {
                    PrivateMsg sentMessage = privateService.sendMessage(username, data.getRoom(), data.getMessage());
                    server.getRoomOperations(username + "_" + data.getRoom()).sendEvent("new_message", new MessageResponse(sentMessage));
                    server.getRoomOperations(data.getRoom() + "_" + username).sendEvent("new_message", new MessageResponse(sentMessage));
                }

            } catch (Exception e) {
                System.err.println("Error processing send message request: " + e.getMessage());
            }
        };
    }

}
