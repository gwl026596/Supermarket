package com.fintek.supermarket;

import android.app.Application;
import android.content.Context;

public class MyAppclication extends Application {
    public static String xAuthToken="";
    public static String xMerchan="";
    public static String xVersion=BuildConfig.VERSION_NAME;
    public static String adid="";
    public static String appName="Supermarket";
    public static String xPackageName=BuildConfig.APPLICATION_ID;
    @Override
    public void onCreate() {
        super.onCreate();
    }
}
