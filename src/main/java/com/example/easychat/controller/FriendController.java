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
    @PostMapping("/add/{userid}/{friendid}")
    public ResponseEntity<String> add(@PathVariable int userid, @PathVariable int friendid, @RequestHeader("Authorization") String auth) {
        if (!userService.validateToken(auth, userid)) {
            return ResponseEntity.badRequest().body("Access denied");
        }

        boolean added = friendService.addFriend(userid, friendid);

        if (added) {
            return ResponseEntity.ok("Add friend success");
        } else {
            return ResponseEntity.badRequest().body("This user is already friend");
        }
    }

    //取得好友清單api
    @GetMapping("/getlist/{userid}")
    public ResponseEntity<String> getList(@PathVariable int userid, @RequestHeader("Authorization") String auth) {
        if (!userService.validateToken(auth, userid)) {
            return ResponseEntity.badRequest().body("Access denied");
        }

        List<Friend> friendList = friendService.getFriendList(userid);
        List<String> jsonList = new ArrayList<>();
        if (friendList.isEmpty()) {
            return ResponseEntity.ok("{}");
        }

        Gson gson = new GsonBuilder().create();

        for (Friend friend : friendList) {
            Optional<User> user = userService.getUser(friend.getFriendId());
            if (user.isPresent()) {

                String json = gson.toJson(user.get());
                jsonList.add(json);

            }
        }
        return ResponseEntity.ok(jsonList.toString());
    }

    //刪除好友api
    @DeleteMapping("/delete/{userid}/{friendid}")
    public ResponseEntity<String> deleteFriend(@PathVariable int userid, @PathVariable int friendid, @RequestHeader("Authorization") String auth) {

        if (!userService.validateToken(auth, userid)) {
            return ResponseEntity.badRequest().body("Access denied");
        }

        Optional<Friend> friend = friendService.getFriend(userid, friendid);
        if (friend.isEmpty()) {

            return ResponseEntity.ok("This user is not your friend yet");

        }

        friendService.deleteFriend(friend.get().getId());


        return ResponseEntity.ok("Friend deleted successfully");
    }


}
