package cn.lemwoodtools.ui.theme

import android.content.Context
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

// 主题模式枚举
enum class ThemeMode {
    LIGHT,      // 浅色模式
    DARK,       // 深色模式
    SYSTEM      // 跟随系统
}

// 创建DataStore
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "theme_settings")

// 主题管理器
class ThemeManager(private val dataStore: DataStore<Preferences>) {
    
    private val themeModeKey = stringPreferencesKey("theme_mode")
    
    // 获取当前主题模式
    val themeMode = dataStore.data.map { preferences ->
        preferences[themeModeKey]?.let { modeName ->
            ThemeMode.valueOf(modeName)
        } ?: ThemeMode.SYSTEM
    }
    
    // 设置主题模式
    suspend fun setThemeMode(mode: ThemeMode) {
        dataStore.edit { preferences ->
            preferences[themeModeKey] = mode.name
        }
    }
    
    // 获取当前是否应该使用深色主题
    fun shouldUseDarkTheme(currentMode: ThemeMode, isSystemInDarkTheme: Boolean): Boolean {
        return when (currentMode) {
            ThemeMode.LIGHT -> false
            ThemeMode.DARK -> true
            ThemeMode.SYSTEM -> isSystemInDarkTheme
        }
    }
}

// 创建主题管理器的Composable函数
@Composable
fun rememberThemeManager(): ThemeManager {
    val context = LocalContext.current
    return remember {
        ThemeManager(context.dataStore)
    }
}

// 获取当前主题模式的Composable函数
@Composable
fun currentThemeMode(): ThemeMode {
    val themeManager = rememberThemeManager()
    val themeMode by themeManager.themeMode.collectAsState(initial = ThemeMode.SYSTEM)
    return themeMode
}

// 设置主题模式的函数
fun setThemeMode(mode: ThemeMode) {
    // 这个函数需要在Composable上下文中调用
    // 实际的主题设置通过LaunchedEffect在ThemeSettings函数中处理
}

// 同步获取主题模式（用于非Composable环境）
fun getThemeModeSync(context: android.content.Context): ThemeMode {
    val themeManager = ThemeManager(context.dataStore)
    return runBlocking {
        themeManager.themeMode.first()
    }
}