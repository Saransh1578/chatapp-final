package com.saransh.chatappbackend.dto;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageResponseDTO {
    Integer messageId;
    Integer senderId;
    Integer receiverId;
    String message;
    Date timestamp;
}
