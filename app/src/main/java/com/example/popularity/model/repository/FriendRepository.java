package com.example.popularity.model.repository;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.example.popularity.model.Friend;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FriendRepository {

    private List<Friend> friends = new ArrayList<>();

    public List<Friend> getFriendsFromMock(String userId) {
        friends.add(new Friend("image1", "hanie", false, userId, 4));
        friends.add(new Friend("image2", "Maryam", false, userId, 8006));
        friends.add(new Friend("image3", "Sara", false, userId, 3));
        friends.add(new Friend("image4", "Parisa", false, userId, 4));
        friends.add(new Friend("image5", "Nava", false, userId, 5));
        friends.add(new Friend("image6", "Samane", false, userId, 6));
        friends.add(new Friend("image6", "Leila", false, userId, 7));
        friends.add(new Friend("image6", "fereshte", false, userId, 8));
        friends.add(new Friend("image6", "Raha", false, userId, 9));

        return friends;
    }

    public List<Friend> getFriendsFromInstagramFollowers() {
        return null;
    }

    public List<Friend> getFriendsFromFacebookFriends() {
        return null;
    }

    public List<Friend> getFriendsFromLinkedinConnections() {
        return null;
    }

    public List<Friend> getFriendsFromPhoneContacts(Context context) {

        ContentResolver cr = context.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                Friend friend = new Friend();
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));
                friend.setId(Integer.parseInt(id));
               // friend.setImage(String.valueOf(R.drawable.ic_user));
                friend.setName(name);
                if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));

                        friend.setUserId(phoneNo);
                        friend.userId = friend.userId.replace("+98", "0");
                        friend.userId = friend.userId.replace(" ", "");
                        friend.userId = friend.userId.trim();
                        if (isValidMobileNumber(friend.userId) && friend.userId != null && friend.name != null) {
                            friends.add(friend);
                        }


                    }
                    pCur.close();
                }
            }
        }
        if (cur != null) {
            cur.close();
        }

        return friends;
    }

    private boolean isValidMobileNumber(String input) {

        //(0|\+98)?([ ]|,|-|[()]){0,2}9[1|2|3|4]([ ]|,|-|[()]){0,2}(?:[0-9]([ ]|,|-|[()]){0,2}){8}
        Pattern pattern = Pattern.compile("^09[0|1|2|3][0-9]{8}$");
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            return true;
        } else
            return false;
    }

}
