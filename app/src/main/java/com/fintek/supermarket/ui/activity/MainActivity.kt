package com.fintek.supermarket.ui.activity

import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import android.webkit.*
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustEvent
import com.fintek.supermarket.BuildConfig
import com.fintek.supermarket.MyAppclication
import com.fintek.supermarket.R
import com.fintek.supermarket.model.JSResponse
import com.fintek.supermarket.model.UserInfoModel
import com.fintek.supermarket.ui.activity.base.BaseActivity
import com.fintek.supermarket.utils.CommonUtils
import com.fintek.supermarket.utils.SharedPreferencesUtils
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject
import wendu.dsbridge.CompletionHandler
import wendu.dsbridge.DWebView
import java.util.*


class MainActivity : BaseActivity() {
     var url:String?=null
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
            val jsonObject = JSONObject(args.toString())
            val type = jsonObject.getString("type")
            val requestParamsData = jsonObject.getString("requestParamsData")
            if (type == "appEvent") {
                val eventToken = when (requestParamsData) {
                    "applyEvent" -> "fcx4vl"
                    "registerEvent" -> "69dk9s"
                    else -> "33fjou"
                }
                val adjustEvent = AdjustEvent(eventToken)
                Adjust.trackEvent(adjustEvent)
            } else if (type == "doBrowser") {
                val uri = Uri.parse(requestParamsData.replace("\"", ""))
                val intent = Intent(Intent.ACTION_VIEW, uri)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity")
                startActivity(intent)
            }else if (type=="navigateToGP"){
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
                MyAppclication.xAuthToken=userInfoModel.token
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
                val token=if(!TextUtils.isEmpty(SharedPreferencesUtils
                        .init(this@MainActivity).getValue("token"))) SharedPreferencesUtils.init(this@MainActivity).getValue("token") else MyAppclication.xAuthToken
                hashMapOf.put("x-auth-token",token)
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
            }else if (type=="getSp"){
                val value = SharedPreferencesUtils.init(this@MainActivity).getValue(requestParamsData)
                handler.complete(CommonUtils.getObjectToString(value))
            }else if (type=="getCurrentServerAddress"){
                handler.complete(CommonUtils.getObjectToString(url?:""))
            }

        }

        @JavascriptInterface
        @Throws(JSONException::class)
        fun doIdentityWithReturnMatch(args: Any?): Any? {
            Log.d("js调用android4", args.toString())
            return args
        }

//     @JavascriptInterface
//     fun asyn(args: Any?, handler: CompletionHandler<String>) {
//         Log.d("js调用androidvv", args.toString())
//         handler.complete(args as String?)
//     }

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
