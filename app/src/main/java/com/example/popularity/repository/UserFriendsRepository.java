package com.example.popularity.repository;

import com.example.popularity.model.Friend;

import java.util.ArrayList;
import java.util.List;

public class UserFriendsRepository {

    public List<Friend> friends=new ArrayList<>();


    public void SetUserFriendsMock(int userId)
    {

       friends.add(new Friend("image1","Hanie",false,userId,1));
       friends.add(new Friend("image2","Maryam",false,userId,2));
       friends.add(new Friend("image3","Sara",false,userId,3));
       friends.add(new Friend("image4","Parisa",false,userId,4));
       friends.add(new Friend("image5","Nava",false,userId,5));
       friends.add(new Friend("image6","Samane",false,userId,6));
       friends.add(new Friend("image6","Leila",false,userId,7));
       friends.add(new Friend("image6","fereshte",false,userId,8));
       friends.add(new Friend("image6","Raha",false,userId,9));

    }

    public List<Friend> getUserFriendsMock(int userId)
    {
        SetUserFriendsMock(userId);
        return friends;
    }
}
