package com.fintek.supermarket.ui.activity

import ai.advance.liveness.lib.LivenessResult
import ai.advance.liveness.sdk.activity.LivenessActivity
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import android.webkit.JavascriptInterface
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustEvent
import com.blankj.utilcode.util.ToastUtils
import com.fintek.httprequestlibrary.BaseApplication
import com.fintek.httprequestlibrary.api.error.HttpError
import com.fintek.httprequestlibrary.api.response.HttpResource
import com.fintek.httprequestlibrary.api.response.LivenessIdResponse
import com.fintek.httprequestlibrary.api.response.LivenessUrlResponse
import com.fintek.httprequestlibrary.api.service.HttpCallback
import com.fintek.httprequestlibrary.api.service.NetHttp
import com.fintek.supermarket.BuildConfig
import com.fintek.supermarket.MyAppclication
import com.fintek.supermarket.R
import com.fintek.supermarket.model.*
import com.fintek.supermarket.ui.activity.base.BaseActivity
import com.fintek.supermarket.utils.CommonUtils
import com.fintek.supermarket.utils.SharedPreferencesUtils
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
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : BaseActivity() {
    /**
     * 请求状态码
     */
    val REQUEST_CODE_LIVENESS: Int = 2002
    val REQUEST_PERMISSION_CAMERA: Int = 2003
    val REQUEST_CODE_TAKE: Int = 2004
    var url: String? = null
    lateinit var  file:File
    lateinit var mHandler: CompletionHandler<String>
    lateinit var mImageHandler: CompletionHandler<String>
    lateinit var mIdNoHandler: CompletionHandler<String>
    lateinit var mIdNoBitmapHandler: CompletionHandler<String>
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
                val eventToken = when (requestModel.requestParamsData) {
                    "applyEvent" -> "fcx4vl"
                    "registerEvent" -> "69dk9s"
                    else -> "33fjou"
                }
                Log.d("换试试",eventToken)
                val adjustEvent = AdjustEvent(eventToken+"==="+requestModel.requestParamsData)
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
            }else if (requestModel.type == "getLivenessBitmap"){
                mImageHandler=handler
            }else if (requestModel.type == "getIdNoOcr"){
                startCamera()
                mIdNoHandler=handler
            }else if (requestModel.type == "getIdNoBitmap"){
                mIdNoBitmapHandler=handler
            }
        }

//     @JavascriptInterface
//     fun asyn(args: Any?, handler: CompletionHandler<String>) {
//         Log.d("js调用androidvv", args.toString())
//         handler.complete(args as String?)
//     }

    }

    private fun startCamera() {

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),REQUEST_PERMISSION_CAMERA)
        } else {
           takePicture(this,REQUEST_CODE_TAKE);
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_CAMERA) {
            if (grantResults.size> 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePicture(this, REQUEST_CODE_TAKE);
            } else {
              ToastUtils.showShort("Silakan buka halaman pengaturan")
            }
        }
    }

    /**
     * 拍照的方法
     */
    fun takePicture(activity: Activity, requestCode: Int) {
        val takePictureIntent =
            Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePictureIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            val format = SimpleDateFormat("yyyyMMddHHmmss")
            val date = Date(System.currentTimeMillis())
            val filename: String = format.format(date)
            val dir = File(getExternalFilesDir(null)?.getPath().toString() + "image")
            if (!dir.exists()) {
                dir.mkdir()
            }
            //创建文件
             file = File("$dir/$filename.png")
            if (!file.exists()) {
                file.createNewFile()
            }
            if (file != null) {
                // 默认情况下，即不需要指定intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                // 照相机有自己默认的存储路径，拍摄的照片将返回一个缩略图。如果想访问原始图片，
                // 可以通过dat extra能够得到原始图片位置。即，如果指定了目标uri，data就没有数据，
                // 如果没有指定uri，则data就返回有数据！
                val uri: Uri
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
                    uri = Uri.fromFile(file)
                } else {
                    /**
                     * 7.0 调用系统相机拍照不再允许使用Uri方式，应该替换为FileProvider
                     * 并且这样可以解决MIUI系统上拍照返回size为0的情况
                     */
                    uri = FileProvider.getUriForFile(
                        activity,
                        "com.example.myapplication.provider",
                        file
                    )
                    //加入uri权限 要不三星手机不能拍照
                    val resInfoList: List<ResolveInfo> = activity.getPackageManager()
                        .queryIntentActivities(
                            takePictureIntent,
                            PackageManager.MATCH_DEFAULT_ONLY
                        )
                    for (resolveInfo in resInfoList) {
                        val packageName: String = resolveInfo.activityInfo.packageName
                        activity.grantUriPermission(
                            packageName,
                            uri,
                            Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
                        )
                    }
                }

                //  Log.e("nanchen", ProviderUtil.getFileProviderName(activity));
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            }
        }
        activity.startActivityForResult(takePictureIntent, requestCode)
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
        if (requestCode == REQUEST_CODE_LIVENESS) {
            if (LivenessResult.isSuccess()) {// 活体检测成功
                val livenessId = LivenessResult.getLivenessId();// 本次活体id
                val bitmap = LivenessResult.getLivenessBitmap();// 本次活体图片
                Log.d("jszzz", livenessId + "==" + bitmap)
                uploadImgAndLicenessId(
                    livenessId,
                    CommonUtils.compressImage(bitmap, this@MainActivity)
                )

            } else {// 活体检测失败
                val errorCode = LivenessResult.getErrorCode();// 失败错误码
                val errorMsg = LivenessResult.getErrorMsg();// 失败原因
            }
        }else if (requestCode === REQUEST_CODE_TAKE) {

            val uri = Uri.fromFile(file)
            Log.d("还是",uri.toString()+"==="+data?.data.toString())
            val imageStream: InputStream? = contentResolver.openInputStream(uri)
            val selectedImage: Bitmap = BitmapFactory.decodeStream(imageStream)
            val encodedImage: String = CommonUtils.getBitmapToBase64(selectedImage)
            uploadOcrBitmap(selectedImage,encodedImage)
        }
    }

    private fun uploadOcrBitmap(selectedImage: Bitmap, encodedImage: String) {
        NetHttp.getInstance().uoloadOcrAdvance(encodedImage,object :HttpCallback<HttpResource<String>>(){
            override fun onSuccess(response: HttpResource<String>?) {
                uploadOcrImage( CommonUtils.compressImage(selectedImage, this@MainActivity))
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
                    uploadImage(livenessId,file)
                }

                override fun onFail(yySportError: HttpError?) {

                }

            })


    }

    fun uploadOcrImage(file: File?) {
       val builder =  MultipartBody.Builder()
            .setType(MultipartBody.FORM)//表单类型
        val body = RequestBody . create (MediaType.parse("multipart/form-data"), file);//表单类型
        builder.addFormDataPart("photo", file?.getName(), body); //添加图片数据，body创建的请求体
        val parts = builder . build ().parts();

        NetHttp.getInstance()
            .uploadImg(parts, "_live_", object : HttpCallback<HttpResource<LivenessUrlResponse>>() {
                override fun onSuccess(response: HttpResource<LivenessUrlResponse>?) {
                   /* val livenessOssId = "fintek-loan-supermarket" + "_live_" + SharedPreferencesUtils.init(this@MainActivity).getValue("userId") + ".png"
                    val jsResponse = JSResponse(1, Gson().toJson(LivenessIdResponseModel(livenessId,livenessOssId)))
                    val livenessJson = Gson().toJson(jsResponse)
                    mHandler.complete(livenessJson)
                    val urlJsResponse = JSResponse(1, Gson().toJson(LivenessUrlResponseModel(response?.data?.url?:"")))
                    val urlJson = Gson().toJson(urlJsResponse)
                    mImageHandler.complete(urlJson)*/

                }

                override fun onFail(yySportError: HttpError?) {

                }

            })
    }
    fun uploadImage(livenessId:String,file: File?) {
       val builder =  MultipartBody.Builder()
            .setType(MultipartBody.FORM)//表单类型
        val body = RequestBody . create (MediaType.parse("multipart/form-data"), file);//表单类型
        builder.addFormDataPart("photo", file?.getName(), body); //添加图片数据，body创建的请求体
        val parts = builder . build ().parts();

        NetHttp.getInstance()
            .uploadImg(parts, "_live_", object : HttpCallback<HttpResource<LivenessUrlResponse>>() {
                override fun onSuccess(response: HttpResource<LivenessUrlResponse>?) {
                    val livenessOssId = "fintek-loan-supermarket" + "_live_" + SharedPreferencesUtils.init(this@MainActivity).getValue("userId") + ".png"
                    val jsResponse = JSResponse(1, Gson().toJson(LivenessIdResponseModel(livenessId,livenessOssId)))
                    val livenessJson = Gson().toJson(jsResponse)
                    mHandler.complete(livenessJson)
                    val urlJsResponse = JSResponse(1, Gson().toJson(LivenessUrlResponseModel(response?.data?.url?:"")))
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

inline fun <reified T : Any> Gson.fromJson(json: String): T {
    return Gson().fromJson(json, T::class.java)
}
