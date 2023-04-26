package com.example.easychat.service;

import com.example.easychat.entity.ChatRoom;
import com.example.easychat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;

    public List<ChatRoom> getChatRooms() {
        return chatRoomRepository.findAll();
    }

    public Optional<ChatRoom> getChatRoom(int chatRoomId) {
        return chatRoomRepository.findById(chatRoomId);
    }

    public Boolean addChatRoom(ChatRoom newChatRoom) {
        Optional<ChatRoom> chatRoom = chatRoomRepository.findByName(newChatRoom.getName());
        if (chatRoom.isPresent()) {
            return false;
        }
        chatRoomRepository.save(newChatRoom);
        return true;
    }

    public Boolean deleteChatRoom(ChatRoom chatRoom, int userId) {
        if (chatRoom.getCreaterId() != userId) {
            return false;
        }

        chatRoomRepository.deleteById(chatRoom.getId());
        return true;
    }

}
