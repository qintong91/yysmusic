package com.example.yys.music;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by qin on 2016/2/17.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        Fresco.initialize(this);
         super.onCreate();
    }

}
