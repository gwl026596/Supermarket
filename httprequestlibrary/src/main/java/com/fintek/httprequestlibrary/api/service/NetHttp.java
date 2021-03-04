package com.fintek.httprequestlibrary.api.service;

import android.text.TextUtils;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SPUtils;
import com.fintek.httprequestlibrary.api.response.HttpResource;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;

public class NetHttp {

    private static NetHttp instance;

    public static NetHttp getInstance() {
        if (instance == null) {
            instance = new NetHttp();
        }
        return instance;
    }
    //开关获取
    public void getIsEnterCurrentApp( HttpCallback<HttpResource<String>> callback) {
        RetrofitManager.getInstance().getService().getIsEnterCurrentApp().enqueue(callback);
    }

    //开关获取
    public void getH5Version( HttpCallback<HttpResource<String>> callback) {
        RetrofitManager.getInstance().getService().getH5Version().enqueue(callback);
    }
}
