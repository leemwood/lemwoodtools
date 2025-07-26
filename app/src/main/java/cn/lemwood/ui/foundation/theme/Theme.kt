package cn.lemwood.ui.foundation.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * Foundation主题系统
 * 轻量级的主题实现，替代Material3的复杂主题系统
 */
@Composable
fun FoundationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colors = when {
        darkTheme -> DarkFoundationColors
        else -> LightFoundationColors
    }
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colors.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }
    
    CompositionLocalProvider(
        LocalFoundationColors provides colors,
        LocalFoundationTypography provides DefaultFoundationTypography,
        LocalFoundationShapes provides DefaultFoundationShapes,
        content = content
    )
}

/**
 * 访问当前主题的颜色
 */
object FoundationTheme {
    val colors: FoundationColors
        @Composable
        get() = LocalFoundationColors.current
    
    val typography: FoundationTypography
        @Composable
        get() = LocalFoundationTypography.current
    
    val shapes: FoundationShapes
        @Composable
        get() = LocalFoundationShapes.current
}