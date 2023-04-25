package com.example.easychat.repository;

import com.example.easychat.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Integer> {

    List<Friend> findAllByUserId(Integer userId);

    Optional<Friend> findByUserIdAndFriendId(int userId, int friendId);

}

