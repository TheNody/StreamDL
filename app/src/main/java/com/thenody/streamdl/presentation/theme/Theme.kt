package com.thenody.streamdl.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext

@Composable
fun StreamDLTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current

    val colorScheme =
        if (dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        } else {
            if (darkTheme) DarkColors else LightColors
        }

    val ext = if (darkTheme) DarkExtended else LightExtended

    CompositionLocalProvider(LocalExtendedColors provides ext) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography  = AppTypography,
            shapes      = AppShapes,
            content     = content
        )
    }
}

object AppTheme {
    val extraColors @Composable get() = LocalExtendedColors.current
}
