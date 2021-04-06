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
import com.fintek.httprequestlibrary.BaseApplication;

import ai.advance.liveness.lib.GuardianLivenessDetectionSDK;
import ai.advance.liveness.lib.Market;

public class MyAppclication extends BaseApplication {

    public static String xMerchan="Kota Emas";
    public static String xVersion=BuildConfig.VERSION_NAME;
    public static String adid="";
    public static String appName="Kota Emas";
    public static String xPackageName=BuildConfig.APPLICATION_ID;
    @Override
    public void onCreate() {
        super.onCreate();
        String appToken = "8491oku9rudc";
        String environment = AdjustConfig.ENVIRONMENT_SANDBOX;
        AdjustConfig config = new AdjustConfig(this, appToken, environment);
        config.setAppSecret(1, 2132576493, 1936989722, 1016585277, 485009239);
        config.setLogLevel(LogLevel.VERBOSE);
        Adjust.onCreate(config);
        registerActivityLifecycleCallbacks(new AdjustLifecycleCallbacks());

        GuardianLivenessDetectionSDK.init(this, "61109d8ff2ad64fd","e7155974a7824c4f", Market.Indonesia);
        GuardianLivenessDetectionSDK.letSDKHandleCameraPermission();
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
