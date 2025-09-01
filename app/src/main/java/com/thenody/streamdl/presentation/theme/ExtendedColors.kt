package com.thenody.streamdl.presentation.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class ExtendedColors(
    val brandAccent: Color,
    val youtube: Color,
    val success: Color,
    val warning: Color,
    val info: Color,
    val errorAlt: Color
)

val LocalExtendedColors = staticCompositionLocalOf {
    ExtendedColors(
        brandAccent = Brand,
        youtube = BrandRed,
        success = Success,
        warning = Warning,
        info = Info,
        errorAlt = ErrorAlt
    )
}

val LightExtended = ExtendedColors(
    brandAccent = Brand,
    youtube = BrandRed,
    success = Success,
    warning = Warning,
    info = Info,
    errorAlt = ErrorAlt
)
val DarkExtended = ExtendedColors(
    brandAccent = BrandDark,
    youtube = BrandRedDark,
    success = SuccessDark,
    warning = WarningDark,
    info = InfoDark,
    errorAlt = ErrorAltDark
)
