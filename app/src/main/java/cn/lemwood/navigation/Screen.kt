package cn.lemwood.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : Screen("home", "首页", Icons.Default.Home)
    object Tools : Screen("tools", "工具", Icons.Default.Build)
    object Search : Screen("search", "搜索", Icons.Default.Search)
    object Settings : Screen("settings", "设置", Icons.Default.Settings)
    
    // Tool screens
    object Calculator : Screen("calculator", "计算器", Icons.Default.Calculate)
    object Converter : Screen("converter", "单位转换", Icons.Default.SwapHoriz)
    object QRCode : Screen("qrcode", "二维码", Icons.Default.QrCode)
    object TextTools : Screen("text_tools", "文本工具", Icons.Default.TextFields)
    object ColorPicker : Screen("color_picker", "颜色选择器", Icons.Default.Palette)
    object Timer : Screen("timer", "计时器", Icons.Default.Timer)
    object Weather : Screen("weather", "天气", Icons.Default.Cloud)
    object Notes : Screen("notes", "笔记", Icons.Default.Note)
}

val bottomNavItems = listOf(
    Screen.Home,
    Screen.Tools,
    Screen.Search,
    Screen.Settings
)