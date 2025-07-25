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
 * 设置管理器
 * 负责管理应用的各种设置选项
 */
object SettingsManager {
    private const val PREFS_NAME = "app_settings"
    private const val KEY_NOTIFICATIONS = "notifications_enabled"
    private const val KEY_HAPTIC_FEEDBACK = "haptic_feedback_enabled"
    
    private val _notificationsEnabled = MutableStateFlow(false)
    val notificationsEnabled: StateFlow<Boolean> = _notificationsEnabled.asStateFlow()
    
    private val _hapticFeedbackEnabled = MutableStateFlow(true)
    val hapticFeedbackEnabled: StateFlow<Boolean> = _hapticFeedbackEnabled.asStateFlow()
    
    private lateinit var prefs: SharedPreferences
    
    /**
     * 初始化设置管理器
     */
    fun initialize(context: Context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        _notificationsEnabled.value = prefs.getBoolean(KEY_NOTIFICATIONS, false)
        _hapticFeedbackEnabled.value = prefs.getBoolean(KEY_HAPTIC_FEEDBACK, true)
    }
    
    /**
     * 设置通知开关
     */
    fun setNotificationsEnabled(enabled: Boolean) {
        _notificationsEnabled.value = enabled
        prefs.edit().putBoolean(KEY_NOTIFICATIONS, enabled).apply()
    }
    
    /**
     * 设置触觉反馈开关
     */
    fun setHapticFeedbackEnabled(enabled: Boolean) {
        _hapticFeedbackEnabled.value = enabled
        prefs.edit().putBoolean(KEY_HAPTIC_FEEDBACK, enabled).apply()
    }
    
    /**
     * 清除应用缓存
     */
    fun clearCache(context: Context): Boolean {
        return try {
            // 清除内部缓存目录
            val cacheDir = context.cacheDir
            deleteRecursive(cacheDir)
            
            // 清除外部缓存目录
            context.externalCacheDir?.let { externalCacheDir ->
                deleteRecursive(externalCacheDir)
            }
            
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
    
    /**
     * 递归删除文件夹
     */
    private fun deleteRecursive(fileOrDirectory: java.io.File): Boolean {
        if (fileOrDirectory.isDirectory) {
            fileOrDirectory.listFiles()?.forEach { child ->
                deleteRecursive(child)
            }
        }
        return fileOrDirectory.delete()
    }
}

/**
 * Compose 辅助函数，用于在 Composable 中获取通知设置状态
 */
@Composable
fun rememberNotificationsEnabled(): Boolean {
    val enabled by SettingsManager.notificationsEnabled.collectAsState()
    return enabled
}

/**
 * Compose 辅助函数，用于在 Composable 中获取触觉反馈设置状态
 */
@Composable
fun rememberHapticFeedbackEnabled(): Boolean {
    val enabled by SettingsManager.hapticFeedbackEnabled.collectAsState()
    return enabled
}