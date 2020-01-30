package com.example.popularity.logic;

import android.content.Context;

import com.example.popularity.model.Friend;
import com.example.popularity.repository.FriendRepository;
import com.example.popularity.utils.LoginKind;

import java.util.List;

public class HomePresenter {
    private FriendRepository friendRepository;
    private Context context;

    public HomePresenter(Context context) {
        this.context = context;
        friendRepository = new FriendRepository();
    }

    public List<Friend> getFriends(LoginKind loginKind, String userId) {
        switch (loginKind){
            case MOCK:
                return friendRepository.getFriendsFromMock(userId);

            case SMS:
                return friendRepository.getFriendsFromPhoneContacts(context);

            default:
                return null;
        }
    }

}
