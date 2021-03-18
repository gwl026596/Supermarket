package com.fintek.httprequestlibrary.api.service;

import com.fintek.httprequestlibrary.api.response.HttpResource;
import com.fintek.httprequestlibrary.api.response.LivenessIdResponse;
import com.fintek.httprequestlibrary.api.response.LivenessUrlResponse;
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

    @Multipart
    @POST("api/common/oss-upload")
    Call<HttpResource<LivenessUrlResponse>> uploadImg(@Part() List<MultipartBody.Part> files , @Query("type") String type);

    //活体检测预处理
    @POST("api/auth/pre-liveness")
    Call<HttpResource<LivenessIdResponse>> uoloadLiveness(@Query("livenessId") String livenessId);

    //ocr信息认证(advance)
    @POST("api/auth/ocr-advance")
    Call<HttpResource<String>> uoloadOcrAdvance(@Query("base64Img") String base64Img);


}
