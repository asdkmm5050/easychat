package com.example.easychat.controller;

import com.example.easychat.entity.User;
import com.example.easychat.service.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 登入API
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> map) {
        String username = map.get("username");
        String password = map.get("password");

        // 檢查使用者名稱和密碼是否為空值
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return ResponseEntity.badRequest().body("Please enter your username/password");
        }

        // 驗證使用者並返回 JWT token
        return ResponseEntity.ok(userService.verifyUser(username, password));
    }

    // 註冊API
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Map<String, String> map) {
        String username = map.get("username");
        String password = map.get("password");

        // 檢查使用者名稱和密碼是否為空值
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return ResponseEntity.badRequest().body("Please enter your username/password");
        }

        // 檢查使用者是否已存在
        if (userService.getUser(username).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        // 建立新的使用者並儲存到資料庫
        User user = new User(username, password);
        userService.addUser(user);

        return ResponseEntity.ok("Registration successful");
    }

    // 查詢使用者API
    @GetMapping("/search/{username}")
    public ResponseEntity<String> getUser(@PathVariable String username, @RequestHeader("Authorization") String auth) {

        // 查詢使用者資訊
        Optional<User> user = userService.getUser(username);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        user.get().setPassword("****");

        Gson gson = new GsonBuilder().create();

        return ResponseEntity.ok(gson.toJson( user.get()));
    }

    // 刪除使用者API
    @DeleteMapping("/delete/{userid}")
    public ResponseEntity<String> deleteUser(@PathVariable int userid, @RequestHeader("Authorization") String auth) {

        // 驗證使用者 token 是否有效
        if (!userService.validateToken(auth, userid)) {
            return ResponseEntity.badRequest().body("Access denied");
        }

        Optional<User> user = userService.getUser(userid);

        // 檢查使用者是否存在
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // 刪除使用者
        userService.deleteUser(user.get().getId());

        return ResponseEntity.ok("User deleted successfully");
    }



}
