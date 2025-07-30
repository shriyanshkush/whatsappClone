package com.example.whatsappclone.Navigation

import android.net.Uri
import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    data object SplashScreen:Routes()

    @Serializable
    data object WelcomScreen:Routes()

    @Serializable
    data object UserRegisterScreen:Routes()

    @Serializable
    data object HomeScreen:Routes()

    @Serializable
    data object UpdateScreen:Routes()

    @Serializable
    data object CommuntiyScreen:Routes()

    @Serializable
    data object CallScreen:Routes()

    @Serializable
    data object UserProfileSetScreen:Routes()

    @Serializable
    data object Settings:Routes()

    @Serializable
    data object ChatScreen : Routes() {
        const val route = "chat_screen/{userId}/{name}/{pfpUrl}"

        fun createRoute(userId: String, name: String, pfpUrl: String): String {
            return "chat_screen/${Uri.encode(userId)}/${Uri.encode(name)}/${Uri.encode(pfpUrl)}"
        }
    }



}