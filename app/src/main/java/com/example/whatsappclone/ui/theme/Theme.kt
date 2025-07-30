package com.example.whatsappclone.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// ✅ Dark theme color scheme for WhatsApp
private val DarkColorScheme = darkColorScheme(
    primary = WhatsAppGreen, // Top bar, tabs
    secondary = WhatsAppLightGreen, // FAB, highlight color
    background = WhatsAppDarkBackground, // Background color
    surface = WhatsAppDarkBackground,
    onPrimary = Color.White,
    onSecondary = Color.Black
)

// ✅ Light theme color scheme for WhatsApp
private val LightColorScheme = lightColorScheme(
    primary = WhatsAppGreen,
    secondary = WhatsAppLightGreen,
    background = WhatsAppBackground,
    surface = WhatsAppBackground,
    onPrimary = Color.White,
    onSecondary = Color.Black
)

@Composable
fun WhatsappCloneTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // ❌ Disable dynamic color for WhatsApp look
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
