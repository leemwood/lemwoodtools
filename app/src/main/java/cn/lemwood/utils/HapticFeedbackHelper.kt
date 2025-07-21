package cn.lemwood.utils

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager

/**
 * 振动反馈助手类
 * 负责管理应用的振动反馈功能
 */
object HapticFeedbackHelper {
    
    /**
     * 获取振动器实例
     */
    private fun getVibrator(context: Context): Vibrator? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
    }
    
    /**
     * 检查设备是否支持振动
     */
    fun hasVibrator(context: Context): Boolean {
        val vibrator = getVibrator(context)
        return vibrator?.hasVibrator() == true
    }
    
    /**
     * 检查设备是否支持振动（别名方法）
     */
    fun isVibrationSupported(context: Context): Boolean {
        return hasVibrator(context)
    }
    
    /**
     * 轻微振动反馈（按钮点击）
     */
    fun lightVibration(context: Context) {
        if (!SettingsManager.hapticFeedbackEnabled.value || !hasVibrator(context)) {
            return
        }
        
        val vibrator = getVibrator(context) ?: return
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(50)
        }
    }
    
    /**
     * 中等振动反馈（成功操作）
     */
    fun mediumVibration(context: Context) {
        if (!SettingsManager.hapticFeedbackEnabled.value || !hasVibrator(context)) {
            return
        }
        
        val vibrator = getVibrator(context) ?: return
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(100)
        }
    }
    
    /**
     * 强烈振动反馈（错误或警告）
     */
    fun strongVibration(context: Context) {
        if (!SettingsManager.hapticFeedbackEnabled.value || !hasVibrator(context)) {
            return
        }
        
        val vibrator = getVibrator(context) ?: return
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(200)
        }
    }
    
    /**
     * 双击振动反馈（特殊操作）
     */
    fun doubleVibration(context: Context) {
        if (!SettingsManager.hapticFeedbackEnabled.value || !hasVibrator(context)) {
            return
        }
        
        val vibrator = getVibrator(context) ?: return
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val pattern = longArrayOf(0, 50, 50, 50)
            val amplitudes = intArrayOf(0, VibrationEffect.DEFAULT_AMPLITUDE, 0, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(VibrationEffect.createWaveform(pattern, amplitudes, -1))
        } else {
            @Suppress("DEPRECATION")
            val pattern = longArrayOf(0, 50, 50, 50)
            vibrator.vibrate(pattern, -1)
        }
    }
    
    /**
     * 成功操作的振动模式
     */
    fun successVibration(context: Context) {
        mediumVibration(context)
    }
    
    /**
     * 错误操作的振动模式
     */
    fun errorVibration(context: Context) {
        strongVibration(context)
    }
    
    /**
     * 按钮点击的振动模式
     */
    fun buttonClickVibration(context: Context) {
        lightVibration(context)
    }
}