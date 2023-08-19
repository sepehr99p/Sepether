package com.example.sepether.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

object Color {


    val md_theme_light_primary = Color(0xFF1A749E)
    val md_theme_light_onPrimary = Color(0xFFFFFFFF)
    val md_theme_light_primaryContainer = Color(0xFF9DCCB9)
    val md_theme_light_onPrimaryContainer = Color(0xFF89F0DB)


    val md_theme_dark_primary = Color(0xFFACD370)
    val md_theme_dark_onPrimary = Color(0xFF213600)
    val md_theme_dark_primaryContainer = Color(0xFF324F00)
    val md_theme_dark_onPrimaryContainer = Color(0xFF324F00)

    public val LightColorScheme = lightColorScheme(
        primary = md_theme_light_primary,
        onPrimary = md_theme_light_onPrimary,
        primaryContainer = md_theme_light_primaryContainer,
        onPrimaryContainer = md_theme_light_onPrimaryContainer,
    )


    public val DarkColorScheme = darkColorScheme(
        primary = md_theme_dark_primary,
        onPrimary = md_theme_dark_onPrimary,
        primaryContainer = md_theme_dark_primaryContainer,
        onPrimaryContainer = md_theme_dark_onPrimaryContainer,

    )

}

