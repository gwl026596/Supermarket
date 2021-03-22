package com.fintek.supermarket.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.text.format.Formatter;


import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by chensongsong on 2020/6/2.
 */
public class MemoryUtils {

    /**
     * 读取内存信息
     *
     * @return
     */
    public static String getTotalMemMemory(Context context) {
        String total="0";
        try {
            ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
            manager.getMemoryInfo(info);
            long totalMem = info.totalMem;
            //long availMem = info.availMem;
            //long usedMem = totalMem - availMem;
             total = Formatter.formatFileSize(context, totalMem);
            //String usable = Formatter.formatFileSize(context, usedMem);
            //String free = Formatter.formatFileSize(context, availMem);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }
    public static String getAvailMemMemMemory(Context context) {
        String free="0";
        try {
            ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
            manager.getMemoryInfo(info);
            long availMem = info.availMem;
            //long usedMem = totalMem - availMem;
             //total = Formatter.formatFileSize(context, totalMem);
            //String usable = Formatter.formatFileSize(context, usedMem);
             free = Formatter.formatFileSize(context, availMem);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return free;
    }
    public static String getUsableMemMemory(Context context) {
        String usable="0";
        try {
            ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
            manager.getMemoryInfo(info);
            long totalMem = info.totalMem;
            long availMem = info.availMem;
            long usedMem = totalMem - availMem;
             //total = Formatter.formatFileSize(context, totalMem);
             usable = Formatter.formatFileSize(context, usedMem);
            //String free = Formatter.formatFileSize(context, availMem);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return usable;
    }



}
