package com.saransh.chatappbackend.controller;


import com.saransh.chatappbackend.dto.MessageDTO;
import com.saransh.chatappbackend.service.MessageService;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;



@RequiredArgsConstructor
@Controller
public class MessageSocketController {
    private final MessageService messageService;

    
    @MessageMapping("/user")
    public void sendUserConversationByUserId(int userId) {
        messageService.sendUserConversationByUserId(userId);
    }

    
    @MessageMapping("/conv")
    public void sendMessagesByConversationId(int conversationId) {
        messageService.sendMessagesByConversationId(conversationId);
    }

   
    @MessageMapping("/sendMessage")
    public void saveMessage(MessageDTO message) {
        messageService.saveMessage(message);
    }

    
}
