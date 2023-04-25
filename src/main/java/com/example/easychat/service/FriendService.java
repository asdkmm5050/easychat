package com.example.easychat.service;

import com.example.easychat.entity.Friend;
import com.example.easychat.entity.User;
import com.example.easychat.repository.FriendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;


    public Optional<Friend> getFriend(int userId, int friendId) {
        return friendRepository.findByUserIdAndFriendId(userId, friendId);
    }

    public List<Friend> getFriendList(int userId) {

        return friendRepository.findAllByUserId(userId);

    }

    public boolean addFriend(int userId, int friendId) {
        Optional<Friend> friend = friendRepository.findByUserIdAndFriendId(userId, friendId);



        if (friend.isPresent()) {
            return false;
        } else {
            Friend newFriend = new Friend(userId, friendId);
            friendRepository.save(newFriend);
            return true;
        }
    }


    public void deleteFriend(int id) {

        friendRepository.deleteById(id);

    }


}
