package com.example.firas.bettertunisia;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Lenovo on 03/11/2016.
 */
public class BetterTunisia extends Application {

    @Override
    public void onCreate()
    {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }

}
