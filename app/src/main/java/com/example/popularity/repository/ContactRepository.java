package com.example.popularity.repository;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.popularity.model.Friend;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactRepository {
    List<Friend> phoneContacts;
    private Friend friend;

    public List<Friend> getContacts(Context context) {

            ArrayList<String> nameList = new ArrayList<>();
            ContentResolver cr = context.getContentResolver();
            Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                    null, null, null, null);
            if ((cur != null ? cur.getCount() : 0) > 0) {
                while (cur != null & cur.moveToNext()) {
                    friend=new Friend();
                    friend.id = Integer.parseInt(cur.getString(
                            cur.getColumnIndex(ContactsContract.Contacts._ID)));
                    friend.name = cur.getString(cur.getColumnIndex(
                            ContactsContract.Contacts.DISPLAY_NAME));
                    nameList.add(friend.name);
                    if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                        Cursor pCur = cr.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                new String[]{friend.userId}, null);
                        while (pCur.moveToNext()) {
                            friend.userId = pCur.getString(pCur.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER));

                        }

                        friend.userId = friend.userId.replace("+98", "0");
                        friend.userId = friend.userId.replace(" ", "");
                        friend.userId = friend.userId.trim();
                        if (friend.userId != null && friend.name != null && IsValidMobileNumber(friend.userId)) {
                            phoneContacts.add(friend);
                        }
                        pCur.close();
                    }
                }
            }
            if (cur != null) {
                cur.close();
            }
            if (nameList != null) {
                Log.i("app_tag", "correct");
            }
            return phoneContacts;


    }

    public static boolean IsValidMobileNumber(String input) {

        //(0|\+98)?([ ]|,|-|[()]){0,2}9[1|2|3|4]([ ]|,|-|[()]){0,2}(?:[0-9]([ ]|,|-|[()]){0,2}){8}
        Pattern pattern = Pattern.compile("^09[0|1|2|3][0-9]{8}$");
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            return true;
        } else
            return false;
    }

}
