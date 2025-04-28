package com.presentation.demo_rag.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

     @PostMapping("/chat")
     public ResponseEntity<String> chat(@RequestBody String message) {
        String body = chatClient.prompt()
                .user(message)
                .call()
                .content();

         return ResponseEntity.ok(body);
     }

}
