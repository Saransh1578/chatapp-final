package com.saransh.chatappbackend.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    Integer conversationId;
    Integer senderId;
    Integer receiverId;
    String message;
    LocalDateTime timestamp;
}
