package com.example.whatsappclone.data.model

data class Message1(
    val messageId: String? = null,      // Unique message key
    val messageText: String? = null,    // Text message content
    val senderId: String? = null,       // Sender UID
//    val senderName: String? = null,     // Optional sender name
    val timestamp: Long = System.currentTimeMillis(),
    val mediaUrl: String? = null,       // Image or video URL
    val mediaType: String? = null,      // "image", "video", "audio", etc.
    val type: String = "text"           // "text" or "media"
)
