package com.fintek.httprequestlibrary;

import android.app.Application;
import android.content.Context;

public class BaseApplication  extends Application {
    public static String xAuthToken="";
    private static BaseApplication app;
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }
    public static BaseApplication getInstance() {
        return app;
    }
}
