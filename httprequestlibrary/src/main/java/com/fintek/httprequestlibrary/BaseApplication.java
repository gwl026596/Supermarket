package com.fintek.httprequestlibrary;

import android.app.Application;

public class BaseApplication  extends Application {
    public static String xAuthToken="";
    @Override
    public void onCreate() {
        super.onCreate();
    }
}
