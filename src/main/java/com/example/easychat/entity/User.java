package com.example.easychat.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "create_time")
    private String createTime = LocalDateTime.now().toString();


    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {

    }

}
