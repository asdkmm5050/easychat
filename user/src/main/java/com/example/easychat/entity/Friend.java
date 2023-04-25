package com.example.easychat.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "friend")
@Data
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "friend_id")
    private int friendId;

    @Column(name = "create_time")
    private String createTime = LocalDateTime.now().toString();

    public Friend(int userId, int friendId) {

        this.userId = userId;
        this.friendId = friendId;

    }

    public Friend() {

    }
}
