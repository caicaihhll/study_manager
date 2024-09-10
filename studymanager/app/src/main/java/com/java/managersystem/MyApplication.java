package com.java.managersystem;

import android.app.Application;

public class MyApplication extends Application {
    private static MyApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        adInit();
    }

    private void adInit(){

    }

    public static MyApplication getInstance(){
        return application;
    }
}
