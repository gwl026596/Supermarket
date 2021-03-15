package com.fintek.supermarket.model

data class JSResponse(
    // java 回调响应码 1 成功 0 失败
    var code : Int = 1,
    // java 返回结果
    var data : String? = null
)