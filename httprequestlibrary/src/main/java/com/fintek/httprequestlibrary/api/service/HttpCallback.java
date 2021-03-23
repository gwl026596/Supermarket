package com.fintek.httprequestlibrary.api.service;

import android.text.TextUtils;

import com.blankj.utilcode.util.ToastUtils;
import com.fintek.httprequestlibrary.api.error.HttpError;
import com.fintek.httprequestlibrary.api.response.HttpResource;
import com.fintek.httprequestlibrary.utils.Constant;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * author:gengwanlong
 * email:2865333106@qq.com
 * time:2019/9/9
 * desc:统一处理异常和错误  多个不同code值重新定义该类
 * version:1.0
 */
public abstract class HttpCallback<T> implements Callback<T> {
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (Constant.RESPONSE_CODE == response.code()) {
            if (((HttpResource) response.body()).getCode().equals(Constant.SUCEESS_CODE)) {
                onSuccess(response.body());
            } else {
                //不是统一错误码，把错误码交给回调处理
                onFail(new HttpError(((HttpResource) response.body()).getCode(),
                        ((HttpResource) response.body()).getMessage()));
            }

        } else {
            onFail(new HttpError(Constant.ERROR_UNKNOWN, response.message()));
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable e) {
        if (e instanceof JsonParseException || e instanceof JSONException) {
            onFail(new HttpError(Constant.ERROR_UNKNOWN, "解析错误"));
        } else if (e instanceof ConnectException || e instanceof UnknownHostException) {
            ToastUtils.showShort("No network link");
            onFail(new HttpError(Constant.ERROR_UNKNOWN, "无网络链接"));
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            onFail(new HttpError(Constant.ERROR_UNKNOWN, "证书验证失败"));
        } else if (e instanceof SocketTimeoutException) {
            ToastUtils.showShort("Jaringan tidak stabil, mohon ganti jaringan lalu coba kembali.");
            onFail(new HttpError(Constant.CODE_000004,"网络异常"));
        } else {
            onFail(new HttpError(Constant.ERROR_UNKNOWN, "未知错误"));
        }
    }



    public abstract void onSuccess(T response);

    public abstract void onFail(HttpError yySportError);
}
