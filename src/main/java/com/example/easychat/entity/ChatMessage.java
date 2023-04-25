package com.example.easychat.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat")
@Data
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "room_id")
    private int chatRoomId;

    @Column(name = "message")
    private String message;

    @Column(name = "sender_id")
    private int senderId;

    @Column(name = "create_time")
    private String createTime = LocalDateTime.now().toString();

}
