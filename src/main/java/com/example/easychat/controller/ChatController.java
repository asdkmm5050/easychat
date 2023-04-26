package com.example.easychat.controller;

import com.example.easychat.entity.ChatRoom;
import com.example.easychat.service.ChatService;
import com.example.easychat.service.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/chat")
public class ChatController {

    private final ChatService chatService;
    private final UserService userService;

    @GetMapping("/room/get_list")
    public ResponseEntity<String> getChatRoomList() {
        List<ChatRoom> chatRooms = chatService.getChatRooms();
        List<String> jsonList = new ArrayList<>();
        if (chatRooms.isEmpty()) {
            return ResponseEntity.ok("{}");
        }

        Gson gson = new GsonBuilder().create();

        for (ChatRoom chatRoom : chatRooms) {
            String json = gson.toJson(chatRoom);
            jsonList.add(json);
        }
        return ResponseEntity.ok(jsonList.toString());
    }

    @PostMapping("/room/add/{user_id}/{chat_room_name}")
    public ResponseEntity<String> addChatRoom(@PathVariable int user_id, @PathVariable String chat_room_name, @RequestHeader("Authorization") String auth) {
        if (!userService.validateToken(auth, user_id)) {
            return ResponseEntity.badRequest().body("Access denied");
        }

        List<ChatRoom> chatRooms = chatService.getChatRooms();
        for (ChatRoom chatRoom : chatRooms) {
            if (chatRoom.getName().equals(chat_room_name)) {
                return ResponseEntity.badRequest().body("This room already exist");
            }
        }

        ChatRoom newChatRoom = new ChatRoom(user_id, chat_room_name);
        chatService.addChatRoom(newChatRoom);


        return ResponseEntity.ok("Add " + chat_room_name);

    }

    @DeleteMapping("/room/delete/{user_id}/{chat_room_id}")
    public ResponseEntity<String> deleteChatRoom(@PathVariable int user_id, @PathVariable int chat_room_id, @RequestHeader("Authorization") String auth) {
        if (!userService.validateToken(auth, user_id)) {
            return ResponseEntity.badRequest().body("Access denied");
        }

        if (chatService.deleteChatRoom(chatService.getChatRoom(chat_room_id).get(), user_id)) {
            return ResponseEntity.ok("Delete success");
        }

        return ResponseEntity.badRequest().body("You don't own this room");
    }


}
