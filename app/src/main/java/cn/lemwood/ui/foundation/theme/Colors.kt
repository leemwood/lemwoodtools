package cn.lemwood.ui.foundation.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * Foundation颜色系统
 * 轻量级的颜色定义，替代Material3的复杂颜色系统
 */
@Immutable
data class FoundationColors(
    // 主要颜色
    val primary: Color = Color(0xFF6750A4),
    val onPrimary: Color = Color(0xFFFFFFFF),
    val primaryContainer: Color = Color(0xFFEADDFF),
    val onPrimaryContainer: Color = Color(0xFF21005D),
    
    // 次要颜色
    val secondary: Color = Color(0xFF625B71),
    val onSecondary: Color = Color(0xFFFFFFFF),
    val secondaryContainer: Color = Color(0xFFE8DEF8),
    val onSecondaryContainer: Color = Color(0xFF1D192B),
    
    // 背景颜色
    val background: Color = Color(0xFFFFFBFE),
    val onBackground: Color = Color(0xFF1C1B1F),
    val surface: Color = Color(0xFFFFFBFE),
    val onSurface: Color = Color(0xFF1C1B1F),
    val surfaceVariant: Color = Color(0xFFE7E0EC),
    val onSurfaceVariant: Color = Color(0xFF49454F),
    
    // 边框和分割线
    val outline: Color = Color(0xFF79747E),
    val outlineVariant: Color = Color(0xFFCAC4D0),
    
    // 错误颜色
    val error: Color = Color(0xFFBA1A1A),
    val onError: Color = Color(0xFFFFFFFF),
    val errorContainer: Color = Color(0xFFFFDAD6),
    val onErrorContainer: Color = Color(0xFF410002),
    
    // 成功颜色（Material3没有的）
    val success: Color = Color(0xFF4CAF50),
    val onSuccess: Color = Color(0xFFFFFFFF),
    val successContainer: Color = Color(0xFFE8F5E8),
    val onSuccessContainer: Color = Color(0xFF1B5E20),
    
    // 警告颜色（Material3没有的）
    val warning: Color = Color(0xFFFF9800),
    val onWarning: Color = Color(0xFFFFFFFF),
    val warningContainer: Color = Color(0xFFFFF3E0),
    val onWarningContainer: Color = Color(0xFFE65100),
    
    // 信息颜色（Material3没有的）
    val info: Color = Color(0xFF2196F3),
    val onInfo: Color = Color(0xFFFFFFFF),
    val infoContainer: Color = Color(0xFFE3F2FD),
    val onInfoContainer: Color = Color(0xFF0D47A1)
)

/**
 * 深色主题颜色
 */
val DarkFoundationColors = FoundationColors(
    primary = Color(0xFFD0BCFF),
    onPrimary = Color(0xFF381E72),
    primaryContainer = Color(0xFF4F378B),
    onPrimaryContainer = Color(0xFFEADDFF),
    
    secondary = Color(0xFFCCC2DC),
    onSecondary = Color(0xFF332D41),
    secondaryContainer = Color(0xFF4A4458),
    onSecondaryContainer = Color(0xFFE8DEF8),
    
    background = Color(0xFF1C1B1F),
    onBackground = Color(0xFFE6E1E5),
    surface = Color(0xFF1C1B1F),
    onSurface = Color(0xFFE6E1E5),
    surfaceVariant = Color(0xFF49454F),
    onSurfaceVariant = Color(0xFFCAC4D0),
    
    outline = Color(0xFF938F99),
    outlineVariant = Color(0xFF49454F),
    
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),
    
    success = Color(0xFF81C784),
    onSuccess = Color(0xFF2E7D32),
    successContainer = Color(0xFF388E3C),
    onSuccessContainer = Color(0xFFC8E6C9),
    
    warning = Color(0xFFFFB74D),
    onWarning = Color(0xFFE65100),
    warningContainer = Color(0xFFFF8F00),
    onWarningContainer = Color(0xFFFFF3E0),
    
    info = Color(0xFF64B5F6),
    onInfo = Color(0xFF1565C0),
    infoContainer = Color(0xFF1976D2),
    onInfoContainer = Color(0xFFE3F2FD)
)

/**
 * 浅色主题颜色
 */
val LightFoundationColors = FoundationColors()

/**
 * 本地组合提供者，用于在组合中访问颜色
 */
val LocalFoundationColors = staticCompositionLocalOf { LightFoundationColors }