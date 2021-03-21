package com.fintek.supermarket.model

data class NeedUploadExtInfoModel(
    val appInfo: Boolean, val userContact: Boolean, val sms: Boolean,
    val callLog: Boolean,
    val equipmentInfo: Boolean,
    val equipmentInfoMap: Boolean,
    val blackbox: Boolean, val gps: Boolean, val imei: Boolean
)