package com.example.easychat.controller;

import com.example.easychat.entity.Friend;
import com.example.easychat.entity.User;
import com.example.easychat.service.FriendService;
import com.example.easychat.service.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/friend")
@RequiredArgsConstructor
public class FriendController {

    private final UserService userService;
    private final FriendService friendService;

    //新增好友api
    @PostMapping("/add/{user_id}/{friend_id}")
    public ResponseEntity<String> add(@PathVariable int user_id, @PathVariable int friend_id, @RequestHeader("Authorization") String auth) {
        if (!userService.validateToken(auth, user_id)) {
            return ResponseEntity.badRequest().body("Access denied");
        }

        Optional<User> user = userService.getUser(friend_id);
        if (user.isPresent()) {
            boolean added = friendService.addFriend(user_id, friend_id);

            if (added) {
                return ResponseEntity.ok("Add friend success");
            } else {
                return ResponseEntity.badRequest().body("This user is already friend");
            }
        }

        return ResponseEntity.badRequest().body("This user is not exist");


    }

    //取得好友清單api
    @GetMapping("/get_list/{user_id}")
    public ResponseEntity<String> getList(@PathVariable int user_id, @RequestHeader("Authorization") String auth) {
        if (!userService.validateToken(auth, user_id)) {
            return ResponseEntity.badRequest().body("Access denied");
        }

        List<Friend> friendList = friendService.getFriendList(user_id);
        List<String> jsonList = new ArrayList<>();
        if (friendList.isEmpty()) {
            return ResponseEntity.ok("{}");
        }

        Gson gson = new GsonBuilder().create();

        for (Friend friend : friendList) {
            Optional<User> user = userService.getUser(friend.getFriendId());
            if (user.isPresent()) {
                user.get().setPassword("****");
                String json = gson.toJson(user.get());
                jsonList.add(json);

            }
        }
        return ResponseEntity.ok(jsonList.toString());
    }

    //刪除好友api
    @DeleteMapping("/delete/{user_id}/{friend_id}")
    public ResponseEntity<String> deleteFriend(@PathVariable int user_id, @PathVariable int friend_id, @RequestHeader("Authorization") String auth) {

        if (!userService.validateToken(auth, user_id)) {
            return ResponseEntity.badRequest().body("Access denied");
        }

        Optional<Friend> friend = friendService.getFriend(user_id, friend_id);
        if (friend.isEmpty()) {

            return ResponseEntity.ok("This user is not your friend yet");

        }

        friendService.deleteFriend(friend.get().getId());


        return ResponseEntity.ok("Friend deleted successfully");
    }


}
