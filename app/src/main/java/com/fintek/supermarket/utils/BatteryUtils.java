package com.fintek.supermarket.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

public class BatteryUtils {



    public static String getBatteryLevel(Context context) {
        int level=0;
        String s="0%";
        try {
            Intent batteryStatus = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            if (batteryStatus != null) {
                 level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                double batteryLevel = -1;
                if (level != -1 && scale != -1) {
                    batteryLevel = DecimalUtils.divide((double) level, (double) scale);
                }
                 s = DecimalUtils.mul(batteryLevel, 100d) + " %";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    public static int getBatteryPercentage(Context context) {
        int level=0;
        try {
            Intent batteryStatus = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            if (batteryStatus != null) {
                 level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return level;
    }

    private static int getAverageCurrent(Context context) {
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                BatteryManager batteryManager = (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);
                return batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_AVERAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取电池容量
     *
     * @param context
     * @return
     */
    @SuppressLint("PrivateApi")
    public static String getBatteryCapacity(Context context) {
        double batteryCapacity = 0;
        try {
            Object mPowerProfile;
            final String powerProfileClass = "com.android.internal.os.PowerProfile";
            mPowerProfile = Class.forName(powerProfileClass)
                    .getConstructor(Context.class)
                    .newInstance(context);
            batteryCapacity = (double) Class.forName(powerProfileClass)
                    .getMethod("getBatteryCapacity")
                    .invoke(mPowerProfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return batteryCapacity + " mAh";
    }

    /**
     * 健康情况
     *
     * @param status
     * @return
     */
    private static String batteryHealth(int status) {
        String health = "Constants.UNKNOWN";
        switch (status) {
            case BatteryManager.BATTERY_HEALTH_COLD:
                health = "Cold";
                break;
            case BatteryManager.BATTERY_HEALTH_DEAD:
                health = "Dead";
                break;
            case BatteryManager.BATTERY_HEALTH_GOOD:
                health = "Good";
                break;
            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                health = "OverVoltage";
                break;
            case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                health = "Overheat";
                break;
            case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                health = "Unknown";
                break;
            case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                health = "Unspecified";
                break;
            default:
                break;
        }
        return health;
    }

    public static boolean getBatteryStatus() {
        Intent batteryStatus = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        if (batteryStatus != null) {
            int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            if (status==BatteryManager.BATTERY_STATUS_CHARGING){
                return  true;
            }else {
                return  false  ;
            }
        }else {
            return  false;
        }
    }

    public static boolean isUsbCharge(){
        Intent batteryStatus = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        if (batteryStatus != null) {
            int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            if (status==BatteryManager.BATTERY_PLUGGED_USB){
                return  true;
            }else {
                return  false  ;
            }
        }
        return  false  ;
    }
    /**
     * 充电状态
     *
     * @param status
     * @return
     */
    private static String batteryStatus(int status) {
        String batteryStatus = "Constants.UNKNOWN";
        switch (status) {
            case BatteryManager.BATTERY_STATUS_CHARGING:
                batteryStatus = "Charging";
                break;
            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                batteryStatus = "DisCharging";
                break;
            case BatteryManager.BATTERY_STATUS_FULL:
                batteryStatus = "Full";
                break;
            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                batteryStatus = "NotCharging";
                break;
            case BatteryManager.BATTERY_STATUS_UNKNOWN:
                batteryStatus = "Unknown";
                break;
            default:
                break;
        }
        return batteryStatus;
    }

    /**
     * 电源
     *
     * @param status
     * @return
     */
    private static String batteryPlugged(int status) {
        String plugged = " Constants.UNKNOWN";
        switch (status) {
            case BatteryManager.BATTERY_PLUGGED_AC:
                plugged = "AC";
                break;
            case BatteryManager.BATTERY_PLUGGED_USB:
                plugged = "USB";
                break;
            case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                plugged = "Wireless";
                break;
            default:
                break;
        }
        return plugged;
    }

}
