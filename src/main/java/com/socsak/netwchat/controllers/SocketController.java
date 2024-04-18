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
import com.socsak.netwchat.services.GroupService;
import com.socsak.netwchat.services.PrivateService;
import com.socsak.netwchat.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;


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

            // TODO : Broadcast to every socket connections
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

            // TODO : Broadcast to every socket connections
        };
    }

    private DataListener<JoinRoomRequest> onJoinRequest() {
        return (senderClient, data, ackSender) -> {
            // TODO : handle user join room (leave current room and join new room, also tell everyone that user join/leave)
            server.getRoomOperations("").sendEvent("user_joined_room");
        };
    }

    private DataListener<CreateGroupRequest> onCreateGroupRequest() {
        return (senderClient, data, ackSender) -> {
            Group group = groupService.createGroup(data.getName());
            System.out.println("Group created [ " + group.getName() + " ']");
            server.getBroadcastOperations().sendEvent("group_created", group);
        };
    }

    private DataListener<SendMessageRequest> onMessageReceived() {
        return (senderClient, data, ackSender) -> {
            // TODO : handle user send message (receive -> do sth. (call services) -> callback to other user)
            senderClient.sendEvent("new_message", new MessageResponse());
        };
    }


}
