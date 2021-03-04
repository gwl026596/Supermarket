package com.fintek.httprequestlibrary.api.service;

import com.fintek.httprequestlibrary.api.response.HttpResource;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by william.geng on 2019/9/9.
 */

public interface HttpService {
    //开关获取
    @POST("api/index/current-app-type")
    Call<HttpResource<String>> getIsEnterCurrentApp();

    //获取H5版本号
    @POST("api/index/current-html-version")
    Call<HttpResource<String>> getH5Version();





}
