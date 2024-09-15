package com.saransh.chatappbackend.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.saransh.chatappbackend.dto.UserResponseDTO;
import com.saransh.chatappbackend.entity.User;


public interface UserService {
    ResponseEntity<UserResponseDTO> saveUser(User user);

    ResponseEntity<UserResponseDTO> findUserByEmail(String email);

    ResponseEntity<UserResponseDTO> findAllUsers();

    ResponseEntity<UserResponseDTO> findAllUsersExceptThisUserId(int userId);

    ResponseEntity<UserResponseDTO> findConversationIdByUser1AndUser2Id(int user1,int user2);
}
