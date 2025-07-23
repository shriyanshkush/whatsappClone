package com.example.whatsappclone.Navigation

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
}