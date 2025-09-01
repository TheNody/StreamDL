package com.thenody.streamdl.app

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.thenody.streamdl.core.navigation.AppEntryPoint
import com.thenody.streamdl.presentation.theme.StreamDLTheme

@Composable
fun StreamDLApp() {
    val isDarkTheme = isSystemInDarkTheme()

    val view = LocalView.current
    val context = LocalContext.current
    val activity = context as Activity

    SideEffect {
        val window = activity.window
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val insetsController = WindowInsetsControllerCompat(window, view)
        insetsController.isAppearanceLightStatusBars = !isDarkTheme
        insetsController.isAppearanceLightNavigationBars = !isDarkTheme

        window.setBackgroundDrawable(null)
    }

    StreamDLTheme(darkTheme = isDarkTheme) {
        AppEntryPoint()
    }
}
