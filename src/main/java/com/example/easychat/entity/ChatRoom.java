package com.example.easychat.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_room")
@Data
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "creater_id")
    private int createrId;

    @Column(name = "create_time")
    private String createTime = LocalDateTime.now().toString();

    public ChatRoom(int createrId, String name) {
        this.name = name;
        this.createrId = createrId;
    }

    public ChatRoom() {

    }
}
