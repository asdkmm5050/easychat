package com.example.easychat.service;

import com.example.easychat.entity.ChatMessage;
import com.example.easychat.entity.ChatRoom;
import com.example.easychat.repository.ChatMessageRepository;
import com.example.easychat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {

    ChatRoomRepository chatRoomRepository;
    ChatMessageRepository chatMessageRepository;

    public List<ChatRoom> getChatRooms() {
        return chatRoomRepository.findAll();
    }

    public Boolean addChatRoom(ChatRoom newChatRoom) {
        Optional<ChatRoom> chatRoom = chatRoomRepository.findByName(newChatRoom.getName());
        if (chatRoom.isPresent()) {
            return false;
        }
        chatRoomRepository.save(newChatRoom);
        return true;
    }

    public List<ChatMessage> getChatMessages(int chatRoomId) {
        return chatMessageRepository.findAllByChatRoomId(chatRoomId);
    }

    public void addChatMessage(ChatMessage chatMessage){
        chatMessageRepository.save(chatMessage);
    }
}
