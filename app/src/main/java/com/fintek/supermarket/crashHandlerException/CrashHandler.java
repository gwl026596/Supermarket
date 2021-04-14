package com.fintek.supermarket.crashHandlerException;

import android.content.Context;
import android.os.Environment;


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
        uploadExceptionToServer();

        if (mDefaultHandler != null) {
            //系统默认的异常处理器来处理,否则由自己来处理
            mDefaultHandler.uncaughtException(t, e);
        } else {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }


    //	上传到Server
    private void uploadExceptionToServer() {
        // TODO
    }

}



