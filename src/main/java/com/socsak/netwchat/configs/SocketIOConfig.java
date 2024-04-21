package com.socsak.netwchat.configs;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocketIOConfig {

    @Value("${socket-host}")
    private String host;
    @Value("${socket-port}")
    private String port;

    @Bean
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration configuration = new com.corundumstudio.socketio.Configuration();
        configuration.setHostname(host);
        configuration.setPort(Integer.parseInt(port));
        configuration.setAllowHeaders("Authorization");
        return new SocketIOServer(configuration);
    }
}
