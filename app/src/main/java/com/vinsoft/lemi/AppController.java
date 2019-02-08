package com.vinsoft.lemi;

import android.app.Application;

public class AppController extends Application {

    public static Application app;

    public static Application getApp() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        app = this;
    }
}
