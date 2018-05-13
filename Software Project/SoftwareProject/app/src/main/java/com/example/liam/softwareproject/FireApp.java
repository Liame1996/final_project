package com.example.liam.softwareproject;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Liam on 28/11/2017.
 */

public class FireApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
    }
}
