package com.example.mrnchatbd.utils;

import android.content.Intent;

import com.example.mrnchatbd.SearchActivity;

public class AndroidUtils {


    public static void passUserModelIntent(Intent intent, SearchActivity.UserModel model){
        intent.putExtra("userName",model.getUserName());
        intent.putExtra("userPhone",model.getPhoneNumber());
        intent.putExtra("userId",model.getUserId());
    }

    public static SearchActivity.UserModel getUserModelIntent(Intent intent){
        SearchActivity.UserModel userModel = new SearchActivity.UserModel();
        userModel.setUserName(intent.getStringExtra("userName"));
        userModel.setPhoneNumber(intent.getStringExtra("userPhone"));
        userModel.setUserId(intent.getStringExtra("userId"));
        return userModel;
    }
}
