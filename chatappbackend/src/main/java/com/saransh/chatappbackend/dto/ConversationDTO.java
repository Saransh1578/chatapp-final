package com.saransh.chatappbackend.dto;

import java.security.Timestamp;

public interface ConversationDTO {
    Integer getConversationId();

    Integer getOtherUserId();

    String getOtherUserName();

    String getLastMessage();

    Timestamp getLastMessageTimestamp();
}

