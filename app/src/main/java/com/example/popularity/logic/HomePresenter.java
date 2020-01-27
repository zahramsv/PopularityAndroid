package com.example.popularity.logic;

import android.app.Activity;
import android.content.Context;

import com.example.popularity.model.Friend;
import com.example.popularity.repository.FriendRepository;

import java.util.List;

public class HomePresenter {
    private FriendRepository friendRepository;
    private Context context;


    public HomePresenter() {
    }

    public HomePresenter(Context context) {
        this.context = context;
        friendRepository = new FriendRepository();
    }

    public List<Friend> getFriends()
    {
        return friendRepository.getFriendsFromPhoneContacts(context);
    }
}
