package com.fintek.supermarket.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import com.fintek.supermarket.R
import com.fintek.supermarket.model.JSResponse
import com.google.gson.Gson
import java.io.ByteArrayOutputStream
import java.util.*


object CommonUtils {

    fun getLogoToString(context: Context): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.app_logo)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val imageBytes: ByteArray = byteArrayOutputStream.toByteArray()
        val imageString: String = Base64.encodeToString(imageBytes, Base64.DEFAULT)
        return imageString
    }

    fun getObjectToString( data:String): String {
        val jsResponse = JSResponse(1, data)
        val gson = Gson()
        val toJson = gson.toJson(jsResponse)
        return toJson
    }
}