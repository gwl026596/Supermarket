package com.fintek.supermarket.model

data class ContractResponseModel (
    var name: String, //联系人名字
    var phone: String, //联系人手机号
    var cutName: String //联系人名字缩略, 如果联系人名字超过20长度就只取前20, 否则什么都不需要做等同于name
)