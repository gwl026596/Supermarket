package com.fintek.supermarket.ui.activity

import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.KeyEvent
import android.webkit.*
import com.fintek.supermarket.MyAppclication
import com.fintek.supermarket.R
import com.fintek.supermarket.model.JSResponse
import com.fintek.supermarket.ui.activity.base.BaseActivity
import com.google.gson.Gson
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject
import wendu.dsbridge.CompletionHandler
import wendu.dsbridge.DWebView
import wendu.dsbridge.OnReturnValue
import kotlin.jvm.Throws


class MainActivity : BaseActivity() {
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
        val url = intent?.getStringExtra("url")
        url?.let {
            webView.loadUrl(it)
        }

    }

    override fun initImmersionBar() {
        super.initImmersionBar()
        ImmersionBar.with(this)
            .transparentStatusBar()
            .fullScreen(false)
            .statusBarDarkFont(true)
            .autoStatusBarDarkModeEnable(true, 0.2f)
            .init()
    }
    /**
     * 初始化WebView
     */
    private fun initWebView() {
        val webSettings: WebSettings = webView.getSettings()
        val dir = this.applicationContext.getDir("database", MODE_PRIVATE).path
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        //允许在WebView中执行javascript
        webSettings.javaScriptEnabled = true
        //设置WebView是否支持使用屏幕控件或手势进行缩放，默认是true，支持缩放。
        webSettings.setSupportZoom(true)
        //自适应任意大小的网页
        webSettings.useWideViewPort = true
        webSettings.loadWithOverviewMode = true
        //阻止图片网络数据
        webSettings.blockNetworkImage = true
        webSettings.javaScriptCanOpenWindowsAutomatically = true
        webSettings.loadsImagesAutomatically = true
        webSettings.setGeolocationEnabled(true)
        webSettings.setGeolocationDatabasePath(dir)
        webSettings.domStorageEnabled = true
        webView.addJavascriptObject(MyJavascriptInterface(), "android")
        //设置WebView浏览器,辅助WebView处理Javascript的对话框，网站图标，网站title，加载进度等.
        webView.setWebChromeClient(object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView, title: String) {
                super.onReceivedTitle(view, title)
            }

            override fun onGeolocationPermissionsShowPrompt(
                origin: String,
                callback: GeolocationPermissions.Callback
            ) {
                // 这里是处理是否同意定位权限，可以在这里写一个 AlertDialog 来模仿浏览器弹出来的定位权限申请。
                //public void invoke(String origin, boolean allow, boolean retain);
                callback.invoke(origin, true, false)
                super.onGeolocationPermissionsShowPrompt(origin, callback)
            }

            override fun onProgressChanged(view: WebView, newProgress: Int) {
//                if (newProgress == 100) {
//                    binding.progressBar.setVisibility(View.GONE)
//                    hide()
//                } else {
//                    if (!binding.progressBar.isShown()) {
//                        binding.progressBar.setVisibility(View.GONE)
//                    }
//                    binding.progressBar.setProgress(newProgress)
//                }
                super.onProgressChanged(view, newProgress)
            }

            override fun onShowFileChooser(
                webView: WebView,
                filePathCallback: ValueCallback<Array<Uri>>,
                fileChooserParams: FileChooserParams
            ): Boolean {

                return true
            }
        })

    }
 inner class MyJavascriptInterface(){
     @JavascriptInterface
     @Throws(JSONException::class)
     fun doSync(args: Any?): Any? {
         Log.d("js调用android1", args.toString())
         return args
     }
     @JavascriptInterface
     @Throws(JSONException::class)
     fun syncSave(args: Any?): Any? {
         Log.d("js调用android2", args.toString())
         return args
     }
     @JavascriptInterface
     @Throws(JSONException::class)
     fun doSyncWithReturn(args: Any?): Any? {
         Log.d("js调用android3", args.toString())
         val jsonObject = JSONObject(args.toString())
         val type = jsonObject.getString("type")
         if(type=="getHttpHeaderJson"){
             val hashMapOf = hashMapOf<String, String>()
             hashMapOf.put("x-auth-token",MyAppclication.xAuthToken)
             hashMapOf.put("x-merchant",MyAppclication.xMerchan)
             hashMapOf.put("x-version",MyAppclication.xVersion)
             hashMapOf.put("adid",MyAppclication.adid)
             hashMapOf.put("app-name",MyAppclication.appName)
             hashMapOf.put("x-package-name",MyAppclication.xPackageName)

             val data = JSONObject(hashMapOf as Map<*, *>)
             Log.d("js调用android3ccff", data.toString())
             val jsResponse=JSResponse(1,data.toString())
             val gson = Gson()
             val toJson = gson.toJson(jsResponse)
             Log.d("js调用android3ccff22",toJson.replace("\\",""))
             webView.callHandler(
                 "getHttpHeaderJson",
                 arrayOf<String>(toJson.replace("\\","")),
                 object : OnReturnValue<String>{
                     override fun onValue(retValue: String?) {
                         Log.d("js调用androidffcff", retValue.toString())
                     }

                 }
             )
         }
         return args
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