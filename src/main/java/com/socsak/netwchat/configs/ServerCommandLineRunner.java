package com.socsak.netwchat.configs;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ServerCommandLineRunner implements CommandLineRunner {

    @Autowired
    private SocketIOServer server;
    @Override
    public void run(String... args) throws Exception {
        server.start();
    }
}
