package com.fintek.supermarket;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustConfig;
import com.adjust.sdk.LogLevel;

public class MyAppclication extends Application {
    public static String xAuthToken="";
    public static String xMerchan="fintek-loan-supermarket";
    public static String xVersion=BuildConfig.VERSION_NAME;
    public static String adid="";
    public static String appName="Supermarket";
    public static String xPackageName=BuildConfig.APPLICATION_ID;
    @Override
    public void onCreate() {
        super.onCreate();
        String appToken = "hbrc72i7o2dc";
        String environment = AdjustConfig.ENVIRONMENT_SANDBOX;
        AdjustConfig config = new AdjustConfig(this, appToken, environment);
        config.setAppSecret(1, 180143412, 1141056911, 755975302, 7869662);
        config.setLogLevel(LogLevel.VERBOSE);
        Adjust.onCreate(config);
        registerActivityLifecycleCallbacks(new AdjustLifecycleCallbacks());
    }
    private static final class AdjustLifecycleCallbacks implements ActivityLifecycleCallbacks {
        @Override
        public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {

        }

        @Override
        public void onActivityStarted(@NonNull Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {
            Adjust.onResume();
        }

        @Override
        public void onActivityPaused(Activity activity) {
            Adjust.onPause();
        }

        @Override
        public void onActivityStopped(@NonNull Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(@NonNull Activity activity) {

        }


    }
}
