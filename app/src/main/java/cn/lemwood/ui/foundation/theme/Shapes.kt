package cn.lemwood.ui.foundation.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

/**
 * Foundation形状系统
 * 简化的形状定义，提供常用的圆角样式
 */
@Immutable
data class FoundationShapes(
    // 无圆角
    val none: Shape = RoundedCornerShape(0.dp),
    
    // 小圆角
    val extraSmall: Shape = RoundedCornerShape(4.dp),
    val small: Shape = RoundedCornerShape(8.dp),
    
    // 中等圆角
    val medium: Shape = RoundedCornerShape(12.dp),
    
    // 大圆角
    val large: Shape = RoundedCornerShape(16.dp),
    val extraLarge: Shape = RoundedCornerShape(28.dp),
    
    // 完全圆形
    val full: Shape = RoundedCornerShape(50),
    
    // 特殊形状
    val card: Shape = RoundedCornerShape(12.dp),
    val button: Shape = RoundedCornerShape(8.dp),
    val chip: Shape = RoundedCornerShape(16.dp),
    val dialog: Shape = RoundedCornerShape(16.dp),
    val bottomSheet: Shape = RoundedCornerShape(
        topStart = 16.dp,
        topEnd = 16.dp,
        bottomStart = 0.dp,
        bottomEnd = 0.dp
    )
)

/**
 * 默认形状系统
 */
val DefaultFoundationShapes = FoundationShapes()

/**
 * 本地组合提供者，用于在组合中访问形状
 */
val LocalFoundationShapes = staticCompositionLocalOf { DefaultFoundationShapes }