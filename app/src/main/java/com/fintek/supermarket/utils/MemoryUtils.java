package com.fintek.supermarket.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by chensongsong on 2020/6/2.
 */
public class MemoryUtils {

    /**
     * 读取内存信息
     *
     * @return
     */
    public static long getTotalMemMemory(Context context) {
        long totalMem = 0;
        try {
            ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
            manager.getMemoryInfo(info);
             totalMem = info.totalMem;
            //long availMem = info.availMem;
            //long usedMem = totalMem - availMem;
            //String usable = Formatter.formatFileSize(context, usedMem);
            //String free = Formatter.formatFileSize(context, availMem);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalMem;
    }

    public static String getAvailMemMemMemory(Context context) {
        String free = "0";
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
        String usable = "0";
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


    /**
     *      * 获取系统总内存
     *      *
     *      * @param context 可传入应用程序上下文。
     *      * @return 总内存大单位为B。
     *     
     */

    public static long getTotalMemorySize(Context context) {
        String dir = "/proc/meminfo";
        try {
            FileReader fr = new FileReader(dir);
            BufferedReader br = new BufferedReader(fr, 2048);
            String memoryLine = br.readLine();
            String subMemoryLine = memoryLine.substring(memoryLine.indexOf("MemTotal:"));
            br.close();
            return Integer.parseInt(subMemoryLine.replaceAll("\\D+", "")) * 1024l;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     *      * 获取手机内部总的存储空间
     *      *
     *      * @return
     *     
     */
    public static long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }

    /**
     *      * 获取系统总内存
     *      *
     *      * @param context 可传入应用程序上下文。
     *      * @return 总内存大单位为B。
     *     
     */
    public static long getTotalMemorySizes(Context context) {
        String dir = "/proc/meminfo";
        try {
            FileReader fr = new FileReader(dir);
            BufferedReader br = new BufferedReader(fr, 2048);
            String memoryLine = br.readLine();
            String subMemoryLine = memoryLine.substring(memoryLine.indexOf("MemTotal:"));
            br.close();
            return Integer.parseInt(subMemoryLine.replaceAll("\\D+", "")) * 1024l;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     *      * 获取手机内部剩余存储空间
     *      *
     *      * @return
     *     
     */
    public static long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }
}
