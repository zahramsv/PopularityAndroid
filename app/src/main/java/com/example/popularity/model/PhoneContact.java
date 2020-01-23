package com.example.popularity.model;

public class PhoneContact {

    String Id;
    String FullName,PhoneNumber;



    public PhoneContact(String id, String fullName, String phoneNumber) {
        Id = id;
        FullName = fullName;
        PhoneNumber = phoneNumber;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }


}
