package com.fintek.supermarket.ui.activity.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fintek.supermarket.R
import com.gyf.immersionbar.ImmersionBar

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initImmersionBar()
        initView()
        initData()
        initListener()
    }

   protected abstract fun getLayoutId():Int
   protected abstract fun initView()
   protected abstract fun initData()
    protected fun initListener(){

    }

    protected open fun initImmersionBar(){
        ImmersionBar.with(this).statusBarColor(R.color.white)
            .statusBarDarkFont(true)
            .fitsSystemWindows(true)
            .keyboardEnable(true).init()
    }


}