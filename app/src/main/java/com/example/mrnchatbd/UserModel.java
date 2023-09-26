package com.example.mrnchatbd;

import java.sql.Timestamp;

public class UserModel {
    private String phoneNumber;
    private String userName;
    private com.google.firebase.Timestamp timestampCreate;


    public UserModel(){

    }

    public UserModel(String phoneNumber, String userName, com.google.firebase.Timestamp timestampCreate) {
        this.phoneNumber = phoneNumber;
        this.userName = userName;
        this.timestampCreate = timestampCreate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public com.google.firebase.Timestamp getTimestampCreate() {
        return timestampCreate;
    }

    public void setTimestampCreate(com.google.firebase.Timestamp timestampCreate) {
        this.timestampCreate = timestampCreate;
    }
}
