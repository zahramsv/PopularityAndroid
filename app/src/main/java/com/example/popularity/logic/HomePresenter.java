package com.example.popularity.logic;

import android.content.Context;

import com.example.popularity.model.Friend;
import com.example.popularity.repository.FriendRepository;

import java.util.List;

public class HomePresenter {
    FriendRepository friendRepository;
    Context context;

    public HomePresenter(Context context) {
        this.context = context;
        friendRepository = new FriendRepository();
    }

    public List<Friend> getFriends()
    {
        return friendRepository.getFriendsFromPhoneContacts(context);
    }
}
