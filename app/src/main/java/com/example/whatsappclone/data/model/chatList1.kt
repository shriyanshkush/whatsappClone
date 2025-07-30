package com.example.whatsappclone.data.model

data class ChatListModel1(
    val reciverUserId: String? = null,
    val reciverName: String? = null,
    val reciverPfpUrl: String? = null,
    val lastMessage: String? = "",
    val lastMessageTimestamp: Long? = 0
)