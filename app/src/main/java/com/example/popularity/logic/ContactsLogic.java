package com.example.popularity.logic;

import android.content.Context;

import com.example.popularity.model.Friend;
import com.example.popularity.repository.ContactRepository;

import java.util.List;

public class ContactsLogic {
    ContactRepository contactRepository;
    Context context;

    public ContactsLogic(Context context) {
        this.context = context;
        contactRepository = new ContactRepository();
    }

    public List<Friend> getContact()
    {
        return contactRepository.getContacts(context);
    }
}
