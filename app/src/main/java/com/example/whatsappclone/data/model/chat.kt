package com.example.whatsappclone.data.model

import android.graphics.Bitmap

data class ChatListModel(
    val name: String?=null,
    val phoneNo:String?=null,
    val image: Int?=null,
    val userId:String?=null,
    val time: String?=null,
    val message: String?=null,
    val profileImage:String?=null

) {
    constructor():this(null,null,null,null,null,null,null)
}
