package com.saransh.chatappbackend.service;


import com.saransh.chatappbackend.dto.MessageDTO;


public interface MessageService {

    void sendUserConversationByUserId(int userId);

    void sendMessagesByConversationId(int conversation_id);

    void saveMessage(MessageDTO message);


}
