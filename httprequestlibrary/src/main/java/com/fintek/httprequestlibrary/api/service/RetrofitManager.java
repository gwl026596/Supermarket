package com.fintek.httprequestlibrary.api.service;



import com.fintek.httprequestlibrary.BuildConfig;
import com.fintek.httprequestlibrary.api.interceptor.HttpHeadersInterceptor;

import java.lang.reflect.Field;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * RetrofitManager请求管理类，多个不同BaseUrl重写getService()替换
 */
public class RetrofitManager {
    private static RetrofitManager instance;
    public  static RetrofitManager getInstance() {
        if (instance == null) {
            instance = new RetrofitManager();
        }
        return instance;
    }
    public HttpService getService(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        OkHttpClient build = new OkHttpClient.Builder().addInterceptor(interceptor)
                .addInterceptor(new HttpHeadersInterceptor())
                .retryOnConnectionFailure(true)
                .connectTimeout(2, TimeUnit.SECONDS).build();

        //获取实例
      return   new Retrofit.Builder()
                //设置OKHttpClient,如果不设置会提供一个默认的
                .client(build)
                //设置baseUrl
                .baseUrl(BuildConfig.API_HOST)
                //添加Gson转换器
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(HttpService.class);
    }


}
