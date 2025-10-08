package cn.lemwoodtools.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFF9A825),  // 浅黄色主色调
    onPrimary = Color(0xFF000000),
    primaryContainer = Color(0xFFFFD54F),
    onPrimaryContainer = Color(0xFF000000),
    secondary = Color(0xFF795548),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFFFD54F),
    onSecondaryContainer = Color(0xFF000000),
    tertiary = Color(0xFF607D8B),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFB0BEC5),
    onTertiaryContainer = Color(0xFF000000),
    background = Color(0xFFFFFBFF),
    onBackground = Color(0xFF1B1B1F),
    surface = Color(0xFFFFFBFF),
    onSurface = Color(0xFF1B1B1F),
    surfaceVariant = Color(0xFFE7E0EC),
    onSurfaceVariant = Color(0xFF49454F),
    outline = Color(0xFF79747E),
    outlineVariant = Color(0xFFCAC4D0),
    error = Color(0xFFBA1A1A),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFFFB300),  // 深一点的黄色
    onPrimary = Color(0xFF000000),
    primaryContainer = Color(0xFFE65100),
    onPrimaryContainer = Color(0xFFFFFFFF),
    secondary = Color(0xFFD7CCC8),
    onSecondary = Color(0xFF000000),
    secondaryContainer = Color(0xFF5D4037),
    onSecondaryContainer = Color(0xFFFFFFFF),
    tertiary = Color(0xFFCFD8DC),
    onTertiary = Color(0xFF000000),
    tertiaryContainer = Color(0xFF455A64),
    onTertiaryContainer = Color(0xFFFFFFFF),
    background = Color(0xFF1B1B1F),
    onBackground = Color(0xFFE4E1E6),
    surface = Color(0xFF1B1B1F),
    onSurface = Color(0xFFE4E1E6),
    surfaceVariant = Color(0xFF49454F),
    onSurfaceVariant = Color(0xFFCAC4D0),
    outline = Color(0xFF938F99),
    outlineVariant = Color(0xFF49454F),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),
)

@Composable
fun LemwoodToolsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

// 新的主题函数，支持主题管理器
@Composable
fun LemwoodToolsThemeWithManager(
    themeMode: ThemeMode = currentThemeMode(),
    content: @Composable () -> Unit
) {
    val isSystemInDarkTheme = isSystemInDarkTheme()
    val shouldUseDarkTheme = rememberThemeManager().shouldUseDarkTheme(themeMode, isSystemInDarkTheme)
    
    LemwoodToolsTheme(
        darkTheme = shouldUseDarkTheme,
        content = content
    )
}