package com.example.easychat.repository;

import com.example.easychat.entity.ChatMessage;
import com.example.easychat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {

    List<ChatMessage> findAllByChatRoomId(int chatRoomId);

}