package com.saransh.chatappbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saransh.chatappbackend.entity.Conversation;
import com.saransh.chatappbackend.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message,Integer>{
    
    List<Message> findAllByConversation(Conversation conversation);
    
}
