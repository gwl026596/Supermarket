package com.fintek.supermarket.utils

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesUtils {
   lateinit var  edit:SharedPreferences.Editor
   lateinit var  sharedPreferences:SharedPreferences
    fun init(context: Context):SharedPreferencesUtils{
         sharedPreferences = context.getSharedPreferences("supermarketSp", Context.MODE_PRIVATE)
         edit = sharedPreferences.edit()
        return this
    }
    fun putValue(key:String,value:String):SharedPreferencesUtils{
        edit.putString(key,value)
        edit.commit()
        return this
    }
    fun getValue(key:String):String{
        return sharedPreferences.getString(key,"")?:""
    }
}