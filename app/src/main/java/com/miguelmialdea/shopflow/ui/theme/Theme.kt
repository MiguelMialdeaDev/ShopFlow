package com.miguelmialdea.shopflow.ui.theme

import android.app.Activity
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

private val DarkColorScheme = darkColorScheme(
    primary = BrandBrownLight,
    secondary = BrandBeige,
    tertiary = StarGold,
    background = TextPrimary,
    surface = Color(0xFF1C1B1F),
    onPrimary = CardWhite,
    onSecondary = TextPrimary,
    onBackground = CardWhite,
    onSurface = CardWhite
)

private val LightColorScheme = lightColorScheme(
    primary = BrandBrown,
    secondary = BrandBrownLight,
    tertiary = StarGold,
    background = BrandBeige,           // Light beige background
    surface = CardWhite,               // White/Cream for cards
    surfaceVariant = BrandCream,       // Alternate card color
    onPrimary = CardWhite,             // White text on brown
    onSecondary = TextPrimary,         // Dark text on beige
    onBackground = TextPrimary,        // Dark text on beige background
    onSurface = TextPrimary,           // Dark text on white cards
    onSurfaceVariant = TextSecondary,  // Gray text
    outline = DividerColor             // Light dividers
)

@Composable
fun ShopFlowTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color disabled for consistent brand colors
    dynamicColor: Boolean = false,
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