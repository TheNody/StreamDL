package com.thenody.streamdl.presentation.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme

val BrandRed = Color(0xFFE11D48)
val BrandRedDark = Color(0xFFB31237)
val Brand = Color(0xFF6750A4)
val BrandDark = Color(0xFF4F378B)

val Success = Color(0xFF16A34A)
val SuccessDark = Color(0xFF22C55E)
val Warning = Color(0xFFF59E0B)
val WarningDark = Color(0xFFFBBF24)
val Info = Color(0xFF0EA5E9)
val InfoDark = Color(0xFF38BDF8)
val ErrorAlt = Color(0xFFEF4444)
val ErrorAltDark = Color(0xFFF87171)

val LightColors = lightColorScheme(
    primary = Brand,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFEADDFF),
    onPrimaryContainer = Color(0xFF21005D),

    secondary = Color(0xFF625B71),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFE8DEF8),
    onSecondaryContainer = Color(0xFF1D192B),

    tertiary = Color(0xFF7D5260),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFFFD8E4),
    onTertiaryContainer = Color(0xFF31111D),

    background = Color(0xFFFBF8FE),
    onBackground = Color(0xFF1C1B1F),

    surface = Color(0xFFFFFBFE),
    onSurface = Color(0xFF1C1B1F),
    surfaceVariant = Color(0xFFE7E0EC),
    onSurfaceVariant = Color(0xFF49454F),

    outline = Color(0xFF79747E),
    outlineVariant = Color(0xFFC9C5D0),

    error = Color(0xFFB3261E),
    onError = Color.White,
    errorContainer = Color(0xFFF9DEDC),
    onErrorContainer = Color(0xFF410E0B),

    inverseSurface = Color(0xFF313033),
    inverseOnSurface = Color(0xFFF4EFF4),
    inversePrimary = Color(0xFFD0BCFF)
)

val DarkColors = darkColorScheme(
    primary = BrandDark,
    onPrimary = Color.White,
    primaryContainer = Color(0xFF4F378B),
    onPrimaryContainer = Color(0xFFEADDFF),

    secondary = Color(0xFFCCC2DC),
    onSecondary = Color(0xFF332D41),
    secondaryContainer = Color(0xFF4A4458),
    onSecondaryContainer = Color(0xFFE8DEF8),

    tertiary = Color(0xFFEFB8C8),
    onTertiary = Color(0xFF492532),
    tertiaryContainer = Color(0xFF633B48),
    onTertiaryContainer = Color(0xFFFFD8E4),

    background = Color(0xFF111114),
    onBackground = Color(0xFFE6E1E6),

    surface = Color(0xFF1A1B1E),
    onSurface = Color(0xFFE6E1E6),
    surfaceVariant = Color(0xFF49454F),
    onSurfaceVariant = Color(0xFFCAC4D0),

    outline = Color(0xFF938F99),
    outlineVariant = Color(0xFF49454F),

    error = Color(0xFFF2B8B5),
    onError = Color(0xFF601410),
    errorContainer = Color(0xFF8C1D18),
    onErrorContainer = Color(0xFFF9DEDC),

    inverseSurface = Color(0xFFE6E1E6),
    inverseOnSurface = Color(0xFF322F35),
    inversePrimary = Color(0xFF381E72)
)
