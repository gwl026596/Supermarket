package com.fintek.supermarket.ui.activity

import ai.advance.liveness.lib.LivenessResult
import ai.advance.liveness.sdk.activity.LivenessActivity
import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Environment
import android.provider.ContactsContract
import android.provider.MediaStore
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import android.webkit.JavascriptInterface
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustEvent
import com.blankj.utilcode.util.DeviceUtils
import com.blankj.utilcode.util.ToastUtils
import com.fintek.httprequestlibrary.BaseApplication
import com.fintek.httprequestlibrary.api.error.HttpError
import com.fintek.httprequestlibrary.api.response.*
import com.fintek.httprequestlibrary.api.service.HttpCallback
import com.fintek.httprequestlibrary.api.service.NetHttp
import com.fintek.supermarket.BuildConfig
import com.fintek.supermarket.MyAppclication
import com.fintek.supermarket.R
import com.fintek.supermarket.model.*
import com.fintek.supermarket.ui.activity.base.BaseActivity
import com.fintek.supermarket.utils.*
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import wendu.dsbridge.CompletionHandler
import wendu.dsbridge.DWebView
import java.io.File
import java.io.FileNotFoundException
import java.util.*
import kotlin.concurrent.thread


class MainActivity : BaseActivity() {
    /**
     * 请求状态码
     */
    val REQUEST_CODE_LIVENESS: Int = 2002
    val REQUEST_PERMISSION_CAMERA: Int = 2003
    val REQUEST_CODE_TAKE: Int = 2004
    val CROP_PICTURE: Int = 2005
    val REQUEST_PERMISSION_READ_CONTACTS: Int = 2005
    val REQUEST_CONTACT_CODE: Int = 2006
    var url: String? = null
    private val IMAGE_FILE_LOCATION =
        "file:///" + Environment.getExternalStorageDirectory().getPath() + "/temp.jpg"
    private val imageUri = Uri.parse(IMAGE_FILE_LOCATION)
    lateinit var mHandler: CompletionHandler<String>
    lateinit var mImageHandler: CompletionHandler<String>
    lateinit var mIdNoHandler: CompletionHandler<String>
    lateinit var mIdNoBitmapHandler: CompletionHandler<String>
    lateinit var mContractHandler: CompletionHandler<String>
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        //initWebView()
    }

    override fun initData() {

        // set debug mode
        DWebView.setWebContentsDebuggingEnabled(true)
        webView.addJavascriptObject(MyJavascriptInterface(), "android")
        url = intent?.getStringExtra("url")
        url?.let {
            webView.loadUrl(it)
        }
        if (!TextUtils.isEmpty(SharedPreferencesUtils.init(this@MainActivity).getValue("token"))) {
            BaseApplication.xAuthToken =
                SharedPreferencesUtils.init(this@MainActivity).getValue("token")
        }
    }
    /* override fun initImmersionBar() {
         super.initImmersionBar()
         ImmersionBar.with(this)
             .fullScreen(false)
             .autoStatusBarDarkModeEnable(true, 0.2f)
             .init()
     }
 */

    inner class MyJavascriptInterface() {
        @JavascriptInterface
        @Throws(JSONException::class)
        fun doSync(args: Any?) {
            Log.d("js调用android1", args.toString())
            val requestModel: RequestModel = Gson().fromJson(args.toString())
            if (requestModel.type == "appEvent") {
                Log.d("换试试", requestModel.requestParamsData.replace("\"", ""))
                val eventToken = when (requestModel.requestParamsData.replace("\"", "")) {
                    "applyEvent" -> "fcx4vl"
                    "registerEvent" -> "69dk9s"
                    else -> "33fjou"
                }
                Log.d("换试试", eventToken)
                val adjustEvent = AdjustEvent(eventToken)
                Adjust.trackEvent(adjustEvent)
            } else if (requestModel.type == "doBrowser") {
                val requestParamsData: String = requestModel.requestParamsData
                Log.d("js调用android1z", requestParamsData.replace("\"", ""))

                val intent = Intent();
                intent.setAction("android.intent.action.VIEW")
                val content_url = Uri.parse(requestParamsData.replace("\"", ""))
                intent.setData(content_url)
                startActivity(intent);


            } else if (requestModel.type == "navigateToGP") {
                try {
                    val uri = Uri.parse("market://details?id=" + BuildConfig.APPLICATION_ID)
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    intent.setPackage("com.android.vending")
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        @JavascriptInterface
        @Throws(JSONException::class)
        fun syncSave(args: Any?) {
            Log.d("js调用android2", args.toString())
            val jsonObject = JSONObject(args.toString())
            val type = jsonObject.getString("type")
            val requestParamsData = jsonObject.getString("requestParamsData")

            if (type == "saveSp") {
                val loginInfo = JSONObject(requestParamsData)
                val userInfoModel: UserInfoModel = Gson().fromJson(loginInfo.getString("loginInfo"))
                SharedPreferencesUtils
                    .init(this@MainActivity)
                    .putValue("phone", userInfoModel.phone)
                    .putValue("token", userInfoModel.token)
                    .putValue("userId", userInfoModel.userId)
                    .putValue("username", userInfoModel.username)
                BaseApplication.xAuthToken = userInfoModel.token
                Log.d(
                    "js调用android2yy", SharedPreferencesUtils
                        .init(this@MainActivity).getValue("phone")
                )
                Log.d(
                    "js调用android2yy3", SharedPreferencesUtils
                        .init(this@MainActivity).getValue("username")
                )
            }
        }

        @JavascriptInterface
        @Throws(JSONException::class)
        fun doSyncWithReturn(args: Any?, handler: CompletionHandler<String>) {
            Log.d("js调用android3", args.toString())
            val jsonObject = JSONObject(args.toString())
            val type = jsonObject.getString("type")
            val requestParamsData = jsonObject.getString("requestParamsData")
            if (type == "getHttpHeaderJson") {
                val hashMapOf = hashMapOf<String, String>()
                val token = if (!TextUtils.isEmpty(
                        SharedPreferencesUtils
                            .init(this@MainActivity).getValue("token")
                    )
                ) SharedPreferencesUtils.init(this@MainActivity)
                    .getValue("token") else MyAppclication.xAuthToken
                hashMapOf.put("x-auth-token", token)
                hashMapOf.put("x-merchant", MyAppclication.xMerchan)
                hashMapOf.put("x-version", MyAppclication.xVersion)
                hashMapOf.put("adid", MyAppclication.adid)
                hashMapOf.put("app-name", MyAppclication.appName)
                hashMapOf.put("x-package-name", MyAppclication.xPackageName)

                val data = JSONObject(hashMapOf as Map<*, *>)

                val jsResponse = JSResponse(1, data.toString())
                val gson = Gson()
                val toJson = gson.toJson(jsResponse)
                Log.d("js调用android3", toJson)
                handler.complete(toJson)

            } else if (type == "getUuid") {
                val uuid: String = UUID.randomUUID().toString()
                handler.complete(CommonUtils.getObjectToString(uuid))
            } else if (type == "getLogo") {
                handler.complete(CommonUtils.getObjectToString(CommonUtils.getLogoToString(this@MainActivity)))
            } else if (type == "getSp") {
                val value = SharedPreferencesUtils.init(this@MainActivity).getValue(
                    requestParamsData
                )
                handler.complete(CommonUtils.getObjectToString(value))
            } else if (type == "getCurrentServerAddress") {
                handler.complete(CommonUtils.getObjectToString(url ?: ""))
            } else if (type == "getContract") {
                mContractHandler = handler
                getAppConfig()
            }

        }

        @JavascriptInterface
        @Throws(JSONException::class)
        fun doIdentityWithReturnMatch(args: Any?, handler: CompletionHandler<String>) {
            Log.d("js调用android4", args.toString())
            val requestModel: RequestModel = Gson().fromJson(args.toString())
            if (requestModel.type == "getLivenessId") {
                mHandler = handler
                startLivenessActivity()
            } else if (requestModel.type == "getLivenessBitmap") {
                mImageHandler = handler
            } else if (requestModel.type == "getIdNoOcr") {
                startCamera()
                mIdNoHandler = handler
            } else if (requestModel.type == "getIdNoBitmap") {
                mIdNoBitmapHandler = handler
            }
        }

//     @JavascriptInterface
//     fun asyn(args: Any?, handler: CompletionHandler<String>) {
//         Log.d("js调用androidvv", args.toString())
//         handler.complete(args as String?)
//     }

    }

    private fun getAppConfig() {

            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_CONTACTS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                startGetAppConfig()
            } else {
                readContacts()
            }

    }

    private fun startCamera() {
        val permissions = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    permissions,
                    REQUEST_PERMISSION_CAMERA
                )
            } else {
                takePhoto()
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_CAMERA) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePhoto()
            } else {
                ToastUtils.showShort("Silakan buka halaman pengaturan")
            }
        }else if (requestCode ==REQUEST_PERMISSION_READ_CONTACTS){
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                readContacts()
            } else {
                ToastUtils.showShort("Silakan buka halaman pengaturan")
            }
        }
    }

    fun startGetAppConfig(){
        val permissions = arrayOf(
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
        )
        val list = mutableListOf<String>()
        list.add("alert_flag")
        list.add("alert_content")
        val appConfigTypeReq = AppConfigTypeReq(list)
        NetHttp.getInstance().getAppConfig(appConfigTypeReq,
            object : HttpCallback<HttpResource<AppConfigResponse>>() {
                override fun onSuccess(response: HttpResource<AppConfigResponse>?) {
                    response?.data?.let {
                        if (it.alert_flag) {
                            AlertDialog.Builder(this@MainActivity)
                                .setMessage(it.alert_content)
                                .setPositiveButton("Setuju",
                                    object : DialogInterface.OnClickListener {
                                        override fun onClick(dialog: DialogInterface?, which: Int) {
                                            ActivityCompat.requestPermissions(
                                                this@MainActivity,
                                                permissions,
                                                REQUEST_PERMISSION_READ_CONTACTS
                                            )
                                        }

                                    }).setNegativeButton("Batalkan",
                                    object : DialogInterface.OnClickListener {
                                        override fun onClick(dialog: DialogInterface?, which: Int) {

                                        }

                                    }).show()
                        }
                    }

                }

                override fun onFail(yySportError: HttpError?) {
                }

            })
    }

    /**
     * 启动系相机
     */
    private fun takePhoto() {
        val intent =
            Intent("android.media.action.IMAGE_CAPTURE")
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        //启动相机（带返回结果）
        startActivityForResult(intent, REQUEST_CODE_TAKE)
    }


    /**
     * 设置裁剪参数，开始裁剪
     * @param uri
     */
    fun startPhotoZoom(uri: Uri?) {
        val intent =
            Intent("com.android.camera.action.CROP")
        intent.setDataAndType(uri, "image/*")
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true")
        intent.putExtra("scale", true)
        intent.putExtra("aspectX", 2) //裁剪框 X 比值
        intent.putExtra("aspectY", 1) //裁剪框 Y 比值
        intent.putExtra("outputX", 600) //裁剪后输出宽度
        intent.putExtra("outputY", 300) //裁剪后输出高度
        intent.putExtra("return-data", false)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri) //输出路径
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
        intent.putExtra("noFaceDetection", true) // no face detection
        startActivityForResult(intent, CROP_PICTURE)
    }

    private fun startLivenessActivity() {
        val intent = Intent(this, LivenessActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE_LIVENESS)
    }

    /**
     * 获取检测结果
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_LIVENESS) {
                if (LivenessResult.isSuccess()) {// 活体检测成功
                    val livenessId = LivenessResult.getLivenessId();// 本次活体id
                    val bitmap = LivenessResult.getLivenessBitmap();// 本次活体图片
                    val bitmaps = LivenessResult.getLivenessBase64Str();// 本次活体图片
                    Log.d("jszzz", livenessId + "==" + bitmaps)
                    uploadImgAndLicenessId(
                        livenessId,
                        CommonUtils.compressImage(bitmap, this@MainActivity)
                    )

                } else {// 活体检测失败
                    val errorCode = LivenessResult.getErrorCode();// 失败错误码
                    val errorMsg = LivenessResult.getErrorMsg();// 失败原因
                }
            } else if (requestCode === REQUEST_CODE_TAKE) {
                startPhotoZoom(imageUri);
            } else if (requestCode === CROP_PICTURE) { // 取得裁剪后的图片
                try {
                    val selectedImage: Bitmap =
                        BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri))
                    getContentResolver().openInputStream(imageUri)?.let {
                        val bitmapToBase64 = CommonUtils.imageToBase64(it)
                        uploadOcrBitmap(selectedImage, bitmapToBase64 ?: "")
                    }
                } catch (e: FileNotFoundException) {
                    e.printStackTrace();
                }
            }else if (requestCode == REQUEST_CONTACT_CODE) {

                try {
                    if (data != null) {
                        val uri = data.getData();
                        lateinit var phoneNum:String;
                        lateinit var contactName:String;
                        // 创建内容解析者
                        val contentResolver = getContentResolver();
                        var cursor:Cursor? = null;
                        if (uri != null) {
                            cursor = contentResolver.query(
                                uri,
                                arrayOf("display_name", "data1"),
                                null,
                                null,
                                null
                            );
                        }
                        while (cursor!!.moveToNext()) {
                            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                            phoneNum = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        }
                        cursor.close();
                        phoneNum = phoneNum.replace("-", " ");
                        phoneNum = phoneNum.replace(" ", "");
                        Log.d("口口声声", contactName + "==" + phoneNum)
                        val cutName= if (contactName.length>20) contactName.substring(0, 20) else contactName
                        val urlJsResponse = JSResponse(
                            1,
                            Gson().toJson(ContractResponseModel(contactName, phoneNum, cutName))
                        )
                        val urlJson = Gson().toJson(urlJsResponse)
                        mContractHandler.complete(urlJson)
                    } else {
                        ToastUtils.showShort("silahkan pilih lagi~")
                    }

                }catch (e: Exception){
                    ToastUtils.showShort("silahkan pilih lagi~")
                }


            }
        }
    }

    //调用并获取联系人信息
    private fun readContacts() {
        thread {  checkExtExpired() }.start()
        val intent =  Intent();
        intent.setAction("android.intent.action.PICK");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("vnd.android.cursor.dir/phone_v2");
        startActivityForResult(intent, REQUEST_CONTACT_CODE);
    }

    private fun checkExtExpired() {
        NetHttp.getInstance().checkExtExpired(object :
            HttpCallback<HttpResource<NeedUploadExtInfoResponse>>() {
            override fun onSuccess(response: HttpResource<NeedUploadExtInfoResponse>?) {
                response?.data?.let {
                    uploadExtInfo(it)
                }

            }

            override fun onFail(yySportError: HttpError?) {

            }

        })
    }

    private fun uploadExtInfo(needUploadExtInfoResponse: NeedUploadExtInfoResponse) {
        if (!TextUtils.isEmpty(SharedPreferencesUtils.init(this@MainActivity).getValue("userId"))){
            val extInfoReqBean= ExtInfoReq.ExtInfoReqBean()
            if (needUploadExtInfoResponse.isUserContact){
                extInfoReqBean.contactList= CommonUtils.getContactList(this@MainActivity)
            }

            if (needUploadExtInfoResponse.isGps){
                val locationManager: LocationManager =
                    this@MainActivity.getSystemService(LOCATION_SERVICE) as LocationManager
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    0,
                    0,
                    LocationListener() {
                        val gpsBean = ExtInfoReq.ExtInfoReqBean.GpsBean()
                        gpsBean.latitude = it.altitude
                        gpsBean.longitude = it.longitude
                        extInfoReqBean.gps = gpsBean
                    })
            }
            if (needUploadExtInfoResponse.isAppInfo){
                extInfoReqBean.appList=CommonUtils.getPkgListNew(this@MainActivity)
            }

            if (needUploadExtInfoResponse.isEquipmentInfoMap){
                val information = System.getProperty("http.agent")
                val imei = CommonUtils.getIMEI(this@MainActivity)
                val gaid= AdvertisingIdClient.getAdvertisingIdInfo(this@MainActivity).id
                val androidId: String = Settings.System.getString(contentResolver, Settings.System.ANDROID_ID)
                val mac = DeviceUtils.getMacAddress()
                val battery = BatteryUtils.getBatteryLevel(this@MainActivity)
                val remoteAddr = GatewayUtils.getIp(this@MainActivity).get("en0")
               val storageTotalSize= MemoryUtils.getTotalMemMemory(this@MainActivity)
               val storageAdjustedTotalSize= MemoryUtils.getAvailMemMemMemory(this@MainActivity)
               val storageAvailableSize= MemoryUtils.getUsableMemMemory(this@MainActivity)
               val sdCardTotalSize= SdUtils.getSdTotalStoreInfo(this@MainActivity)
               val sdCardAvailableSize= SdUtils.getSdUsableSpaceStoreInfo(this@MainActivity)
               val imsi = CommonUtils.getSubscriberId(this@MainActivity)
               val isRoot = RootUtils.isRoot(this@MainActivity)
               val isLocServiceEnable = CommonUtils.isLocServiceEnable(this@MainActivity)
               val isNetwork = if (NetWorkUtils.isNetworkConnected(this@MainActivity)) "1" else "0"
               val language = LanguageUtils.getDefaultLanguage(this@MainActivity)
//               val equipmentInfoMapBeans= ExtInfoReq.ExtInfoReqBean.EquipmentInfoMapBean(
//                   information,
//                   imei,
//                   gaid,androidId,mac,battery,remoteAddr,storageTotalSize,storageAdjustedTotalSize,storageAvailableSize,
//                   sdCardTotalSize,sdCardAvailableSize,imsi,isRoot,isLocServiceEnable,isNetwork,language
//               )

                //extInfoReqBean.equipmentInfoMap=equipmentInfoMapBeans
            }
            extInfoReqBean.userId=SharedPreferencesUtils.init(this@MainActivity).getValue("userId")
            val extInfoReq=ExtInfoReq(extInfoReqBean)
            NetHttp.getInstance().extInfo(extInfoReq,
                object : HttpCallback<HttpResource<String>>() {
                    override fun onSuccess(response: HttpResource<String>?) {

                    }

                    override fun onFail(yySportError: HttpError?) {

                    }

                })
        }

    }




    private fun uploadOcrBitmap(selectedImage: Bitmap, encodedImage: String) {
        Log.d("base64", encodedImage)
        NetHttp.getInstance()
            .uoloadOcrAdvance(
                encodedImage.substring(0, encodedImage.length - 100),
                object : HttpCallback<HttpResource<OcrRespomse>>() {
                    override fun onSuccess(response: HttpResource<OcrRespomse>?) {
                        uploadOcrImage(
                            response?.data,
                            CommonUtils.compressImage(selectedImage, this@MainActivity)
                        )
                    }

                    override fun onFail(yySportError: HttpError?) {

                    }

                })
    }

    private fun uploadImgAndLicenessId(livenessId: String, file: File?) {
        NetHttp.getInstance()
            .uoloadLiveness(livenessId, object : HttpCallback<HttpResource<LivenessIdResponse>>() {
                override fun onSuccess(response: HttpResource<LivenessIdResponse>?) {
                    Log.d("js调用android4", response?.data?.livenessId ?: "" + "===" + mHandler)
                    uploadImage(livenessId, file)
                }

                override fun onFail(yySportError: HttpError?) {

                }

            })


    }

    fun uploadOcrImage(ocrRespomse: OcrRespomse?, file: File?) {
        val builder = MultipartBody.Builder()
            .setType(MultipartBody.FORM)//表单类型
        val body = RequestBody.create(MediaType.parse("multipart/form-data"), file);//表单类型
        builder.addFormDataPart("photo", file?.getName(), body); //添加图片数据，body创建的请求体
        val parts = builder.build().parts();

        NetHttp.getInstance()
            .uploadImg(parts, "_ocr_", object : HttpCallback<HttpResource<LivenessUrlResponse>>() {
                override fun onSuccess(response: HttpResource<LivenessUrlResponse>?) {
                    val ocrOssId =
                        "fintek-loan-supermarket" + "_ocr_" + SharedPreferencesUtils.init(this@MainActivity)
                            .getValue("userId") + ".png"
                    val jsResponse = JSResponse(
                        1,
                        Gson().toJson(
                            OcrModel(
                                ocrRespomse?.idCardData?.name ?: "",
                                ocrRespomse?.idCardData?.nik ?: "",
                                ocrOssId
                            )
                        )
                    )
                    val livenessJson = Gson().toJson(jsResponse)
                    mIdNoHandler.complete(livenessJson)
                    val urlJsResponse =
                        JSResponse(1, Gson().toJson(OcrImageModel(response?.data?.url ?: "")))
                    val urlJson = Gson().toJson(urlJsResponse)
                    mIdNoBitmapHandler.complete(urlJson)

                }

                override fun onFail(yySportError: HttpError?) {

                }

            })
    }

    fun uploadImage(livenessId: String, file: File?) {
        val builder = MultipartBody.Builder()
            .setType(MultipartBody.FORM)//表单类型
        val body = RequestBody.create(MediaType.parse("multipart/form-data"), file);//表单类型
        builder.addFormDataPart("photo", file?.getName(), body); //添加图片数据，body创建的请求体
        val parts = builder.build().parts();

        NetHttp.getInstance()
            .uploadImg(parts, "_live_", object : HttpCallback<HttpResource<LivenessUrlResponse>>() {
                override fun onSuccess(response: HttpResource<LivenessUrlResponse>?) {
                    val livenessOssId =
                        "fintek-loan-supermarket" + "_live_" + SharedPreferencesUtils.init(this@MainActivity)
                            .getValue("userId") + ".png"
                    val jsResponse = JSResponse(
                        1,
                        Gson().toJson(LivenessIdResponseModel(livenessId, livenessOssId))
                    )
                    val livenessJson = Gson().toJson(jsResponse)
                    mHandler.complete(livenessJson)
                    val urlJsResponse = JSResponse(
                        1,
                        Gson().toJson(LivenessUrlResponseModel(response?.data?.url ?: ""))
                    )
                    val urlJson = Gson().toJson(urlJsResponse)
                    mImageHandler.complete(urlJson)

                }

                override fun onFail(yySportError: HttpError?) {

                }

            })
    }


    override fun onDestroy() {
        super.onDestroy()
        webView.destroy()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            // 返回上一页面
            webView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}

private fun LocationManager.requestLocationUpdates(
    networkProvider: String,
    i: Int,
    i1: Int,
    locationListener: LocationListener
) {

}

inline fun <reified T : Any> Gson.fromJson(json: String): T {
    return Gson().fromJson(json, T::class.java)
}
