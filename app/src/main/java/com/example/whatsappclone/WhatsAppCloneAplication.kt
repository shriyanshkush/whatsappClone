package com.example.whatsappclone
import android.app.Application
import com.cloudinary.android.MediaManager
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class WhatsAppCloneAplication:Application() {
    override fun onCreate() {
        super.onCreate()
        val config: HashMap<String, String> = hashMapOf(
            "cloud_name" to cloudname,
            "secure" to "true"
        )
        MediaManager.init(this, config)
    }
}