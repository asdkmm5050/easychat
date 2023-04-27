package com.example.easychat.service;

import com.example.easychat.entity.User;
import com.example.easychat.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> getUser(String username) {

        return userRepository.findByUsername(username);
    }

    public Optional<User> getUser(int userId) {

        return userRepository.findById(userId);
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public String verifyUser(String username, String password) {

        Optional<User> user = userRepository.findByUsername(username);
        String result;
        if (user.isPresent()) {
            if (user.get().getPassword().equals(password)) {
                Date expireDate = new Date(System.currentTimeMillis() + 60 * 60 * 1000);
                result = Jwts.builder()
                        .setSubject(username)
                        .setExpiration(expireDate)
                        .signWith(SignatureAlgorithm.HS512, "peko")
                        .compact();

            } else {
                result = "wrong password";
            }
        } else {
            result = "wrong username";
        }
        return result;
    }

    public boolean validateToken(String token, int userId) {
        try {
            if (!token.startsWith("Bearer ")) {
                return false;
            }
            Optional<User> user = userRepository.findById(userId);
            if (user.isEmpty()) {
                return false;
            }

            String username = user.get().getUsername();

            token = token.substring(7); // 去除 "Bearer " 字串
            Claims claims = Jwts.parser().setSigningKey("peko").parseClaimsJws(token).getBody();
            String payload = claims.getSubject();

            return Objects.equals(payload, username);

        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}
