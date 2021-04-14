package com.fintek.httprequestlibrary.api.service;

import android.text.TextUtils;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SPUtils;
import com.fintek.httprequestlibrary.api.response.AppConfigResponse;
import com.fintek.httprequestlibrary.api.response.AppConfigTypeReq;
import com.fintek.httprequestlibrary.api.response.ExtInfoReq;
import com.fintek.httprequestlibrary.api.response.HttpResource;
import com.fintek.httprequestlibrary.api.response.LivenessIdResponse;
import com.fintek.httprequestlibrary.api.response.LivenessUrlResponse;
import com.fintek.httprequestlibrary.api.response.NeedUploadExtInfoResponse;
import com.fintek.httprequestlibrary.api.response.OcrRespomse;
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

    //活体检测预处理
    public void uoloadLiveness(String livenessId, HttpCallback<HttpResource<LivenessIdResponse>> callback) {
        RetrofitManager.getInstance().getService().uoloadLiveness(livenessId).enqueue(callback);
    }
    //活体检测预处理
    public void uploadImg(List<MultipartBody.Part> files,String type, HttpCallback<HttpResource<LivenessUrlResponse>> callback) {
        RetrofitManager.getInstance().getService().uploadImg(files,type).enqueue(callback);
    }
    //ocr信息认证(advance)
    public void uoloadOcrAdvance(String base64Img, HttpCallback<HttpResource<OcrRespomse>> callback) {
        RetrofitManager.getInstance().getService().uoloadOcrAdvance(base64Img).enqueue(callback);
    }
    //上报debug日志
    public void uoloadDebug(String content, String type, String version, HttpCallback<HttpResource<Boolean>> callback) {
        RetrofitManager.getInstance().getService().uoloadDebug(content,type,version).enqueue(callback);
    }
    //获取appConfig字典数据
    public void getAppConfig(AppConfigTypeReq appConfigTypeReq, HttpCallback<HttpResource<AppConfigResponse>> callback) {
        RetrofitManager.getInstance().getService().getAppConfig(appConfigTypeReq).enqueue(callback);
    }
    //上传用户扩展数据
    public void extInfo(ExtInfoReq extInfoReq, HttpCallback<HttpResource<String>> callback) {
        RetrofitManager.getInstance().getService().extInfo(extInfoReq).enqueue(callback);
    }
    //检查用户扩展信息是否过期
    public void checkExtExpired( HttpCallback<HttpResource<NeedUploadExtInfoResponse>> callback) {
        RetrofitManager.getInstance().getService().checkExtExpired().enqueue(callback);
    }
}
