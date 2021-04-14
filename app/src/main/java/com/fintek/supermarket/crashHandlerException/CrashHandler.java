package com.fintek.supermarket.crashHandlerException;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.fintek.httprequestlibrary.api.error.HttpError;
import com.fintek.httprequestlibrary.api.response.HttpResource;
import com.fintek.httprequestlibrary.api.response.OcrRespomse;
import com.fintek.httprequestlibrary.api.service.HttpCallback;
import com.fintek.httprequestlibrary.api.service.NetHttp;
import com.fintek.supermarket.BuildConfig;

import java.io.PrintWriter;
import java.io.StringWriter;


public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "CrashHandler";
    private static final String PATH = Environment.getExternalStorageDirectory() + "/crash/log/";

    private Context mContext;
    private volatile static CrashHandler mCrashHandler;
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        if (mCrashHandler == null) {
            synchronized (CrashHandler.class) {
                if (mCrashHandler == null) {
                    mCrashHandler = new CrashHandler();
                }
            }
        }
        return mCrashHandler;
    }

    public void init(Context context) {
        mContext = context.getApplicationContext();
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        uploadExceptionToServer(e);

        if (mDefaultHandler != null) {
            //系统默认的异常处理器来处理,否则由自己来处理
            mDefaultHandler.uncaughtException(t, e);
        } else {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }


    //	上传到Server
    private void uploadExceptionToServer(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        printWriter.print("Android版本号 :");
        printWriter.println(Build.VERSION.RELEASE);
        printWriter.print("手机品牌 :");
        printWriter.println(Build.BRAND);
        printWriter.print("设备型号 :");
        printWriter.println(Build.DEVICE);
        throwable.printStackTrace(printWriter);
        printWriter.close();
        Log.d("接电话的",stringWriter.toString());
        NetHttp.getInstance().uoloadDebug(stringWriter.toString(), "崩溃日志", BuildConfig.VERSION_NAME, new HttpCallback<HttpResource<Boolean>>() {
            @Override
            public void onSuccess(HttpResource<Boolean> response) {

            }

            @Override
            public void onFail(HttpError yySportError) {

            }
        });
    }

}



