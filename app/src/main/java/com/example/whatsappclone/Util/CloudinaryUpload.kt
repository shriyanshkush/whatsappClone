package com.example.whatsappclone.util

import android.content.Context
import android.net.Uri
import android.util.Log
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.example.whatsappclone.uploadpreset
import com.example.whatsappclone.uploadpresetname

object CloudinaryUploader {

    fun uploadImage(
        context: Context,
        imageUri: Uri,
        folderPath: String, // e.g., "whatsapp-clone/users/uid123" or "whatsapp-clone/chats/chat123"
        fileName: String,   // e.g., "profile.jpg" or "msg_172833.jpeg"
        onResult: (url: String?) -> Unit
    ) {
        val publicId = "$folderPath/$fileName"

        MediaManager.get().upload(imageUri)
            .unsigned(uploadpreset)
            .option(uploadpresetname, publicId)
            .callback(object : UploadCallback {
                override fun onStart(requestId: String?) {
                    Log.d("CloudinaryUploader", "Upload started")
                }

                override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {
                    val progress = (bytes * 100 / totalBytes).toInt()
                    Log.d("CloudinaryUploader", "Upload progress: $progress%")
                }

                override fun onSuccess(requestId: String?, resultData: Map<*, *>?) {
                    val secureUrl = resultData?.get("secure_url") as? String
                    Log.d("CloudinaryUploader", "Upload success: $secureUrl")
                    onResult(secureUrl)
                }

                override fun onError(requestId: String?, error: ErrorInfo?) {
                    Log.e("CloudinaryUploader", "Upload error: ${error?.description}")
                    onResult(null)
                }

                override fun onReschedule(requestId: String?, error: ErrorInfo?) {
                    Log.w("CloudinaryUploader", "Upload rescheduled: ${error?.description}")
                }
            })
            .dispatch()
    }

}
