package com.saransh.chatappbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class UserResponseDTO {
    private Integer statusCode;
    private String status;
    private String reason;
    private Object data;
}
