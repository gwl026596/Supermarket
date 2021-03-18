package com.fintek.supermarket.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Base64
import com.fintek.supermarket.R
import com.fintek.supermarket.model.JSResponse
import com.google.gson.Gson
import java.io.*
import java.text.SimpleDateFormat
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

    fun getObjectToString(data: String): String {
        val jsResponse = JSResponse(1, data)
        val gson = Gson()
        val toJson = gson.toJson(jsResponse)
        return toJson
    }

    fun compressImage(bitmap: Bitmap,context: Context):File{
        val baos = ByteArrayOutputStream()
        bitmap.compress(
            Bitmap.CompressFormat.JPEG,
            100,
            baos
        ) //质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中

        var options = 100
        while (baos.toByteArray().size / 1024 > 500) {  //循环判断如果压缩后图片是否大于500kb,大于继续压缩
            baos.reset() //重置baos即清空baos
            options -= 10 //每次都减少10
            bitmap.compress(
                Bitmap.CompressFormat.JPEG,
                options,
                baos
            ) //这里压缩options%，把压缩后的数据存放到baos中
            val length = baos.toByteArray().size.toLong()
        }
        val format = SimpleDateFormat("yyyyMMddHHmmss")
        val date = Date(System.currentTimeMillis())
        val filename: String = format.format(date)
        //创建文件路径
        //创建文件路径
        val dir = File(context.getExternalFilesDir(null)?.getPath().toString() + "image")
        if (!dir.exists()) {
            dir.mkdir()
        }
        //创建文件
        //创建文件
        val file = File("$dir/$filename.png")
        if (!file.exists()) {
            file.createNewFile()
        }

        try {
            val fos = FileOutputStream(file)
            try {
                fos.write(baos.toByteArray())
                fos.flush()
                fos.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        recycleBitmap(bitmap)
        return file

    }

    fun recycleBitmap(vararg bitmaps: Bitmap){
        if (bitmaps == null) {
            return
        }
        for (bm in bitmaps) {
            if (null != bm && !bm.isRecycled) {
                bm.recycle()
            }
        }

    }
}