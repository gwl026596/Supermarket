package com.fintek.supermarket.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.LocationManager
import android.os.Build
import android.provider.ContactsContract
import android.provider.Settings
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.Base64
import android.util.DisplayMetrics
import android.view.Display
import android.view.WindowManager
import com.fintek.httprequestlibrary.api.response.ExtInfoReq
import com.fintek.supermarket.R
import com.fintek.supermarket.model.JSResponse
import com.google.gson.Gson
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


object CommonUtils {

    fun getBitmapToBase64(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }
    fun getBase64ToBitmap(base64: String): Bitmap? {
        var bitmap:Bitmap? = null;
        try {
            val bitmapByte = Base64.decode(base64, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapByte, 0, bitmapByte.size);
        } catch (e:Exception ) {
            e.printStackTrace();
        }
         return bitmap
    }

    fun imageToBase64(inputStream: InputStream): String? {
        val data: ByteArray
        var result: String? = null
        try {
            //创建一个字符流大小的数组。
            data = ByteArray(inputStream.available())
            //写入数组
            inputStream.read(data)
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data, Base64.NO_WRAP)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return result
    }


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

    /**
     * 将图片转换成Base64编码的字符串
     */
    fun imagePathToBase64(path: String?): String? {
        if (TextUtils.isEmpty(path)) {
            return null
        }
        var inputStream: InputStream? = null
        var data: ByteArray? = null
        var result: String? = null
        try {
            inputStream = FileInputStream(path)
            //创建一个字符流大小的数组。
            data = ByteArray(inputStream.available())
            //写入数组
            inputStream.read(data)
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data, Base64.NO_CLOSE)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return result
    }
    fun compressImage(bitmap: Bitmap, context: Context):File{
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

     fun getPkgListNew(context: Context): List<ExtInfoReq.ExtInfoReqBean.AppListBean>? {
        val appLists= mutableListOf<ExtInfoReq.ExtInfoReqBean.AppListBean>()
        try {
            val packageInfos: List<PackageInfo> = context.getPackageManager().getInstalledPackages(
                PackageManager.GET_ACTIVITIES or
                        PackageManager.GET_SERVICES
            )
            for (info in packageInfos) {
                val pkgName: String = info.packageName
                val inTime: Long = info.firstInstallTime
                val upTime: Long = info.lastUpdateTime
                val versionCode: Int = info.versionCode
                val versionName: String = info?.versionName?:"1.0.0"
                val appName: String = info.applicationInfo.loadLabel(context.getPackageManager()).toString()
                val flags: Int = info.applicationInfo.flags
                val appType: String = if (isSystemApp(info)) "1" else "0"
                val appListBean= ExtInfoReq.ExtInfoReqBean.AppListBean(
                    appName,
                    appType,
                    flags.toString(),
                    inTime.toString(),
                    pkgName,
                    upTime.toString(),
                    versionCode.toString(),
                    versionName
                )
                appLists.add(appListBean)
            }
        } catch (t: Throwable) {
            t.printStackTrace()
        }
        return appLists
    }

    // 通过packName得到PackageInfo，作为参数传入即可
     fun isSystemApp(pi: PackageInfo): Boolean {
        val isSysApp = pi.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM === 1
        val isSysUpd = pi.applicationInfo.flags and ApplicationInfo.FLAG_UPDATED_SYSTEM_APP === 1
        return isSysApp || isSysUpd
    }

    fun getContactList(context: Context):MutableList<ExtInfoReq.ExtInfoReqBean.ContactListBean>{
        val contactLists= mutableListOf<ExtInfoReq.ExtInfoReqBean.ContactListBean>()
        var cursor: Cursor?=null
        try {

            cursor = context.getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null
            );
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    val displayName = cursor.getString(
                        cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                        )
                    )
                    val number = cursor.getString(
                        cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                        )
                    )
                    val id = cursor.getString(
                        cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone._ID
                        )
                    )
                    val inVisibleGroup = cursor.getString(
                        cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.IN_VISIBLE_GROUP
                        )
                    )
                    val isUserProfile =0
                    val lastTimeContacted = cursor.getString(
                        cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.LAST_TIME_CONTACTED
                        )
                    )
                    val sendToVoicemail = cursor.getString(
                        cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.SEND_TO_VOICEMAIL
                        )
                    )
                    val starred = cursor.getString(
                        cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.STARRED
                        )
                    )
                    val timesContacted = cursor.getString(
                        cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.TIMES_CONTACTED
                        )
                    )
                    val upTime = cursor.getString(
                        cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.CONTACT_LAST_UPDATED_TIMESTAMP
                        )
                    )
                    val phoneCount = cursor.getInt(
                        cursor
                            .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)
                    )
                    contactLists.add(
                        ExtInfoReq.ExtInfoReqBean.ContactListBean(
                            phoneCount.toString(),
                            id,
                            inVisibleGroup,
                            isUserProfile.toString(),
                            lastTimeContacted,
                            displayName,
                            number,
                            sendToVoicemail,
                            starred,
                            timesContacted,
                            upTime
                        )
                    )

                }

            }
        } catch (e: Exception) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return contactLists
    }

    /**
     * 获取设备的唯一标识， 需要 “android.permission.READ_Phone_STATE”权限
     */
    @SuppressLint("MissingPermission")
    fun getIMEI(context: Context): String? {
        var deviceId:String=""
        val tm: TelephonyManager = context
            .getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        if (Build.VERSION.SDK_INT < 29){
            deviceId = tm.deviceId
        }else{
            deviceId = getUniqueID(context)?:""
        }

        return deviceId
    }


    private fun getUniqueID(context: Context): String? {
        var id: String? = null
        val androidId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        if (!TextUtils.isEmpty(androidId) && "9774d56d682e549c" != androidId) {
            try {
                val uuid = UUID.nameUUIDFromBytes(androidId.toByteArray(charset("utf8")))
                id = uuid.toString()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        if (TextUtils.isEmpty(id)) {
            id = getUUID()
        }
        return if (TextUtils.isEmpty(id)) UUID.randomUUID().toString() else id
    }
    private fun getUUID(): String? {
        var serial: String? = null
        val m_szDevIDShort = "35" + Build.BOARD.length % 10 + Build.BRAND.length % 10 + (if (null != Build.CPU_ABI) Build.CPU_ABI.length else 0) % 10 + Build.DEVICE.length % 10 + Build.DISPLAY.length % 10 + Build.HOST.length % 10 + Build.ID.length % 10 + Build.MANUFACTURER.length % 10 + Build.MODEL.length % 10 + Build.PRODUCT.length % 10 + Build.TAGS.length % 10 + Build.TYPE.length % 10 + Build.USER.length % 10 //13 位
        if (Build.VERSION.SDK_INT <= 29) {
            try {
                serial = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Build.getSerial()
                } else {
                    Build.SERIAL
                }
                //API>=9 使用serial号
                return UUID(m_szDevIDShort.hashCode().toLong(), serial.hashCode().toLong()).toString()
            } catch (exception: java.lang.Exception) {
                serial = "serial" // 随便一个初始化
            }
        } else {
            serial = Build.UNKNOWN // 随便一个初始化
        }

        //使用硬件信息拼凑出来的15位号码
        return UUID(m_szDevIDShort.hashCode().toLong(), serial.hashCode().toLong()).toString()
    }

    @SuppressLint("MissingPermission")
    fun getSubscriberId(context: Context?): String? {
        var mSubscriberId:String=""
        val tm: TelephonyManager = context?.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        if (Build.VERSION.SDK_INT < 29){
            mSubscriberId = tm?.getSubscriberId()?:"" // String
        }else{
            mSubscriberId = getUniqueID(context)?:""
        }
        return mSubscriberId
    }

    /**
     * 手机是否开启位置服务，如果没有开启那么所有app将不能使用定位功能
     */
    fun isLocServiceEnable(context: Context): String {
        val locationManager: LocationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val gps: Boolean = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val network: Boolean = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        return if (gps || network) "true" else "false"
    }

    fun getSize(context: Context):Double{
        val wm: WindowManager =
            context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display: Display = wm.getDefaultDisplay()
        // 屏幕宽度
        // 屏幕宽度
        val screenWidth: Float = display.getWidth().toFloat()
        // 屏幕高度
        // 屏幕高度
        val screenHeight: Float = display.getHeight().toFloat()
        val dm = DisplayMetrics()
        display.getMetrics(dm)
        val x = Math.pow(dm.widthPixels / dm.xdpi.toDouble(), 2.0)
        val y = Math.pow(dm.heightPixels / dm.ydpi.toDouble(), 2.0)
        // 屏幕尺寸
        // 屏幕尺寸
        return Math.sqrt(x + y)
    }
}