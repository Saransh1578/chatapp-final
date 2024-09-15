package com.saransh.chatappbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.saransh.chatappbackend.dto.LoginRequestDTO;
import com.saransh.chatappbackend.dto.UserResponseDTO;
import com.saransh.chatappbackend.entity.User;
import com.saransh.chatappbackend.service.UserService;


@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody User user) {
        return userService.saveUser(user);
    }

    
    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        return userService.findUserByEmail(loginRequest.getEmail());
    }

   
    @GetMapping("/all")
    public ResponseEntity<UserResponseDTO> findAllUsers() {
        return userService.findAllUsers();
    }

  
    @GetMapping("/except/{userId}")
    public ResponseEntity<UserResponseDTO> findAllUsersExceptThisUserId(@PathVariable int userId) {
        return userService.findAllUsersExceptThisUserId(userId);
    }

   
    @GetMapping("/conversation/id")
    public ResponseEntity<UserResponseDTO> findConversationIdByUser1IdAndUser2Id(@RequestParam int user1Id, @RequestParam int user2Id) {
        return userService.findConversationIdByUser1AndUser2Id(user1Id, user2Id);
    }
}
