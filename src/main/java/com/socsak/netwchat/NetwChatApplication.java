package com.socsak.netwchat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class NetwChatApplication {
	@RequestMapping("/")
	String home() {
		return "Hello Java!";
	}

	public static void main(String[] args) {
		SpringApplication.run(NetwChatApplication.class, args);
	}

}
