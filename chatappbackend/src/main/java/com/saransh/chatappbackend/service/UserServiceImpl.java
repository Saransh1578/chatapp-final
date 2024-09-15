package com.saransh.chatappbackend.service;

import lombok.RequiredArgsConstructor;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.saransh.chatappbackend.dto.UserResponseDTO;
import com.saransh.chatappbackend.entity.Conversation;
import com.saransh.chatappbackend.entity.User;
import com.saransh.chatappbackend.repository.ConversationRepository;
import com.saransh.chatappbackend.repository.UserRepository;
import java.util.Optional;
import java.util.List;


@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;

    
    @Override
    public ResponseEntity<UserResponseDTO> saveUser(User user) {
        try {
            user = userRepository.save(user);
            return new ResponseEntity<>(
                    UserResponseDTO.builder()
                            .statusCode(200)
                            .status("Success")
                            .reason("OK")
                            .data(user)
                            .build(),
                    HttpStatus.OK
            );
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(
                    UserResponseDTO.builder()
                            .statusCode(200)
                            .status("Failed")
                            .reason("Email already registered")
                            .data(null)
                            .build(),
                    HttpStatus.OK
            );
        }

    }

   
    @Override
    public ResponseEntity<UserResponseDTO> findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return new ResponseEntity<>(
                    UserResponseDTO.builder()
                            .statusCode(200)
                            .status("Success")
                            .reason("OK")
                            .data(user)
                            .build(),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(
                    UserResponseDTO.builder()
                            .statusCode(200)
                            .status("Failed")
                            .reason("User not found")
                            .data(null)
                            .build(),
                    HttpStatus.OK
            );
        }
    }

  
    @Override
    public ResponseEntity<UserResponseDTO> findAllUsers() {
        List<User> list = userRepository.findAll();
        return new ResponseEntity<>(
                UserResponseDTO.builder()
                        .statusCode(200)
                        .status("Success")
                        .reason("OK")
                        .data(list)
                        .build(),
                HttpStatus.OK
        );
    }

   
    @Override
    public ResponseEntity<UserResponseDTO> findAllUsersExceptThisUserId(int userId) {
        List<User> list = userRepository.findAllUsersExceptThisUserId(userId);
        return new ResponseEntity<>(
                UserResponseDTO.builder()
                        .statusCode(200)
                        .status("Success")
                        .reason("OK")
                        .data(list)
                        .build(),
                HttpStatus.OK
        );
    }

   
    @Override
    public ResponseEntity<UserResponseDTO> findConversationIdByUser1AndUser2Id(int user1Id, int user2Id) {
        int conversationId;
        Optional<User> user1 = userRepository.findById(user1Id);
        Optional<User> user2 = userRepository.findById(user2Id);
        if (user1.isEmpty() || user2.isEmpty()) {
            return new ResponseEntity<>(
                    UserResponseDTO.builder()
                            .statusCode(200)
                            .status("Failed")
                            .reason("User not found")
                            .data(null)
                            .build(),
                    HttpStatus.OK
            );
        }

        Optional<Conversation> existingConversation = conversationRepository.findConversationByUsers(user1.get(), user2.get());
        if (existingConversation.isPresent()) {
            conversationId = existingConversation.get().getConversationId();
        } else {
            Conversation newConversation = new Conversation();
            newConversation.setUser1(user1.get());
            newConversation.setUser2(user2.get());
            Conversation savedConversation = conversationRepository.save(newConversation);
            conversationId = savedConversation.getConversationId();
        }
        return new ResponseEntity<>(
                UserResponseDTO.builder()
                        .statusCode(200)
                        .status("Success")
                        .reason("OK")
                        .data(conversationId)
                        .build(),
                HttpStatus.OK
        );
    }
        
}
