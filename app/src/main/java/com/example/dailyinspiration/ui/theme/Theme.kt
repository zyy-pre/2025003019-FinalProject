package com.example.dailyinspiration.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = Green40,
    onPrimary = Green99,
    primaryContainer = Green90,
    onPrimaryContainer = Green10,
    secondary = Teal40,
    onSecondary = Green99,
    secondaryContainer = Teal90,
    onSecondaryContainer = Teal10,
    tertiary = Purple20,
    onTertiary = Green99,
    tertiaryContainer = Purple10,
    onTertiaryContainer = Green99,
    background = Green99,
    onBackground = Green10,
    surface = Green99,
    onSurface = Green10,
    surfaceVariant = Green95,
    onSurfaceVariant = Green30
)

private val DarkColorScheme = darkColorScheme(
    primary = Green80,
    onPrimary = Green20,
    primaryContainer = Green30,
    onPrimaryContainer = Green90,
    secondary = Teal80,
    onSecondary = Teal20,
    secondaryContainer = Teal30,
    onSecondaryContainer = Teal90,
    tertiary = Green80,
    onTertiary = Green20,
    tertiaryContainer = Green30,
    onTertiaryContainer = Green90,
    background = Green10,
    onBackground = Green90,
    surface = Green10,
    onSurface = Green90,
    surfaceVariant = Green30,
    onSurfaceVariant = Green80
)

@Composable
fun DailyInspirationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
