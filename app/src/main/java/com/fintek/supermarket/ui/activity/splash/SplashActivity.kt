package com.fintek.supermarket.ui.activity.splash

import android.content.Intent
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustEvent
import com.fintek.httprequestlibrary.BuildConfig
import com.fintek.httprequestlibrary.api.error.HttpError
import com.fintek.httprequestlibrary.api.response.HttpResource
import com.fintek.httprequestlibrary.api.service.HttpCallback
import com.fintek.httprequestlibrary.api.service.NetHttp
import com.fintek.supermarket.R
import com.fintek.supermarket.ui.activity.MainActivity
import com.fintek.supermarket.ui.activity.base.BaseActivity

class SplashActivity : BaseActivity() {
    private var isEntertag: Boolean = false
    private var isH5VersionTag: Boolean = false
    private var url: String = BuildConfig.URL_H5

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initView() {

    }

    override fun initData() {
        if (!isTaskRoot) {
            finish()
        }
        val adjustEvent = AdjustEvent("ae67pt")
        Adjust.trackEvent(adjustEvent)
        NetHttp.getInstance().getIsEnterCurrentApp(object : HttpCallback<HttpResource<String>>() {
            override fun onSuccess(response: HttpResource<String>?) {
                response?.let {
                    isEntertag = it.data == "SUPER_MARKET"
                }
                enterMain()
            }

            override fun onFail(yySportError: HttpError?) {
                isEntertag = true;
                enterMain()
            }

        })
        NetHttp.getInstance().getH5Version(object : HttpCallback<HttpResource<String>>() {
            override fun onSuccess(response: HttpResource<String>?) {
                isH5VersionTag = true
                response?.let {
                    url = BuildConfig.URL_H5_HEADER + it.data + BuildConfig.URL_H5_FOOTER
                }
                enterMain()
            }

            override fun onFail(yySportError: HttpError?) {
                isH5VersionTag = true
                enterMain()
            }

        })
    }

    private fun enterMain() {
        if (isEntertag && isH5VersionTag) {
            startActivity(
                Intent(this@SplashActivity, MainActivity::class.java).putExtra("url", url)
            )
            finish()
        }
    }

}