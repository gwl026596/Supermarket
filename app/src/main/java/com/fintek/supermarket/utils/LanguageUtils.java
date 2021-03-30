package com.fintek.supermarket.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import java.util.Locale;
import java.util.TimeZone;


/**
 * Created by chensongsong on 2020/8/20.
 */
public class LanguageUtils {

    public static Context updateResources(Context context, String language) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateLanguageInHigher(context, language);
        } else {
            return updateLanguageInLower(context, language);
        }
    }


    public static String getDefaultLanguage(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String language = Locale.getDefault().getLanguage() + "-" + Locale.getDefault().getCountry();
        String lang = preferences.getString("settings_language", language);
        if (TextUtils.isEmpty(lang) || TextUtils.equals(lang, "default")) {
            lang = language;
        }
        return lang;
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static Context updateLanguageInHigher(Context context, String language) {
        Resources resources = context.getResources();
        Locale locale = getLocale(context, language);
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        LocaleList localeList = new LocaleList(locale);
        LocaleList.setDefault(localeList);
        configuration.setLocales(localeList);
        return context.createConfigurationContext(configuration);
    }

    private static Context updateLanguageInLower(Context context, String language) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale locale = getLocale(context, language);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
            return context.createConfigurationContext(configuration);
        } else {
            configuration.locale = locale;
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
            return context;
        }
    }

    private static Locale getLocale(Context context, String language) {
        try {
            String[] split = language.split("-");
            if (split.length == 1) {
                return new Locale(split[0]);
            } else if (split.length == 2) {
                return new Locale(split[0], split[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return context.getResources().getConfiguration().locale;
    }

    @SuppressWarnings("deprecation")
    public static void changeAppLanguage(Context context, String language) {
        Resources resources = context.getResources();
        Configuration configuration = new Configuration(resources.getConfiguration());
        Locale locale = new Locale(language);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList localeList = new LocaleList(locale);
            LocaleList.setDefault(localeList);
            configuration.setLocale(locale);
            configuration.setLocales(localeList);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }
    /**
     * 获取当前时区
     * @return
     */
    public static String getCurrentTimeZone() {
        TimeZone tz = TimeZone.getDefault();
        String strTz = tz.getDisplayName(false, TimeZone.SHORT);
        return strTz;

    }
}
