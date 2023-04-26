package com.example.easychat.repository;

import com.example.easychat.entity.ChatRoom;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {

    Optional<ChatRoom> findByName(String name);
}
