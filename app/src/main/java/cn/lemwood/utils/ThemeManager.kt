package cn.lemwood.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * 主题管理器
 * 负责管理应用的深色主题设置
 */
object ThemeManager {
    private const val PREFS_NAME = "theme_prefs"
    private const val KEY_DARK_THEME = "dark_theme"
    
    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()
    
    private lateinit var prefs: SharedPreferences
    
    /**
     * 初始化主题管理器
     */
    fun initialize(context: Context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        _isDarkTheme.value = prefs.getBoolean(KEY_DARK_THEME, false)
    }
    
    /**
     * 设置深色主题
     */
    fun setDarkTheme(isDark: Boolean) {
        _isDarkTheme.value = isDark
        prefs.edit().putBoolean(KEY_DARK_THEME, isDark).apply()
    }
    
    /**
     * 切换主题
     */
    fun toggleTheme() {
        setDarkTheme(!_isDarkTheme.value)
    }
    
    /**
     * 获取当前是否为深色主题
     */
    fun getCurrentTheme(): Boolean = _isDarkTheme.value
}

/**
 * Compose 辅助函数，用于在 Composable 中获取当前主题状态
 */
@Composable
fun rememberIsDarkTheme(): Boolean {
    val isDarkTheme by ThemeManager.isDarkTheme.collectAsState()
    return isDarkTheme
}