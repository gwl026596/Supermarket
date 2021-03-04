package com.fintek.httprequestlibrary.api.interceptor;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class HttpHeadersInterceptor implements Interceptor {

    @SuppressLint("MissingPermission")
    @NonNull
    private Headers headers() {

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type",  "application/json");
        headers.put("Accept",  "application/json");
        headers.put("x-auth-token",  "");
        headers.put("x-merchant",  "fintek-loan-supermarket");
        headers.put("x-version",  "1.0.0");
        headers.put("x-package-name",  "com.fintek.supermarket");
        return Headers.of(headers);
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request original = chain.request();
        Request request = original.newBuilder()
                .headers(headers())
                .method(original.method(), original.body())
                .build();

        return chain.proceed(request);
    }
}
