package com.saransh.chatappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.saransh.chatappbackend.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.userId<> ?1")
    List<User>findAllUsersExceptThisUserId(int userId);
}
