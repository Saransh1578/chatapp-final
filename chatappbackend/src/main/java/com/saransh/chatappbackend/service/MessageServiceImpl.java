package com.saransh.chatappbackend.service;


import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.saransh.chatappbackend.dto.ConversationDTO;
import com.saransh.chatappbackend.dto.MessageDTO;
import com.saransh.chatappbackend.dto.MessageResponseDTO;
import com.saransh.chatappbackend.dto.WebSocketResponseDTO;
import com.saransh.chatappbackend.entity.Conversation;
import com.saransh.chatappbackend.entity.Message;
import com.saransh.chatappbackend.entity.User;
import com.saransh.chatappbackend.repository.ConversationRepository;
import com.saransh.chatappbackend.repository.MessageRepository;
import com.saransh.chatappbackend.repository.UserRepository;

import java.sql.Date;
import java.time.ZoneId;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final SimpMessagingTemplate messagingTemplate;
    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;

    
    @Override
    public void sendUserConversationByUserId(int userId) {
        List<ConversationDTO> conversation = conversationRepository.findConversationsByUserId(userId);
        messagingTemplate.convertAndSend(
                "/topic/user/".concat(String.valueOf(userId)),
                WebSocketResponseDTO.builder()
                        .type("ALL")
                        .data(conversation)
                        .build()
        );
    }

    
    @Override
    public void sendMessagesByConversationId(int conversationId) {
        Conversation conversation = new Conversation();
        conversation.setConversationId(conversationId);
        List<Message> messageList = messageRepository.findAllByConversation(conversation);
        List<MessageResponseDTO> messageResponseList = messageList.stream()
                .map((message -> MessageResponseDTO.builder()
                        .messageId(message.getMessageId())
                        .message(message.getMessage())
                        .timestamp(Date.from(message.getTimestamp().atZone(ZoneId.systemDefault()).toInstant()))
                        .senderId(message.getSender().getUserId())
                        .receiverId(message.getReceiver().getUserId())
                        .build())
                ).toList();
        messagingTemplate.convertAndSend("/topic/conv/".concat(String.valueOf(conversationId)), WebSocketResponseDTO.builder()
                .type("ALL")
                .data(messageResponseList)
                .build()
        );
    }

   
    @Override
    public void saveMessage(MessageDTO msg) {
        User sender = userRepository.findById(msg.getSenderId()).get();
        User receiver = userRepository.findById(msg.getReceiverId()).get();
        Conversation conversation = conversationRepository.findConversationByUsers(sender, receiver).get();
        Message newMessage = new Message();
        newMessage.setMessage(msg.getMessage());
        newMessage.setTimestamp(msg.getTimestamp());
        newMessage.setConversation(conversation);
        newMessage.setSender(sender);
        newMessage.setReceiver(receiver);
        Message savedMessage = messageRepository.save(newMessage);
        MessageResponseDTO res = MessageResponseDTO.builder()
                .messageId(savedMessage.getMessageId())
                .message(savedMessage.getMessage())
                .timestamp(Date.from(savedMessage.getTimestamp().atZone(ZoneId.systemDefault()).toInstant()))
                .senderId(savedMessage.getSender().getUserId())
                .receiverId(savedMessage.getReceiver().getUserId())
                .build();
        messagingTemplate.convertAndSend("/topic/conv/".concat(msg.getConversationId().toString()),
                WebSocketResponseDTO.builder()
                        .type("ADDED")
                        .data(res)
                        .build()
        );
        sendUserConversationByUserId(msg.getSenderId());
        sendUserConversationByUserId(msg.getReceiverId());
    }

    
}
