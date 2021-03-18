package com.fintek.supermarket.ext

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.blankj.utilcode.util.ToastUtils

public fun Context.toast(text:String){
    Toast.makeText(this,text,Toast.LENGTH_LONG).show()
}