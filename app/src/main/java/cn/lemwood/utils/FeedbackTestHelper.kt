package cn.lemwood.utils

import android.content.Context
import android.widget.Toast

/**
 * 反馈测试助手类
 * 用于测试振动和通知反馈功能
 */
object FeedbackTestHelper {
    
    /**
     * 测试所有振动类型
     */
    fun testAllVibrations(context: Context) {
        if (!HapticFeedbackHelper.isVibrationSupported(context)) {
            Toast.makeText(context, "设备不支持振动", Toast.LENGTH_SHORT).show()
            return
        }
        
        if (!SettingsManager.hapticFeedbackEnabled.value) {
            Toast.makeText(context, "振动反馈已禁用", Toast.LENGTH_SHORT).show()
            return
        }
        
        // 测试按钮点击振动
        HapticFeedbackHelper.buttonClickVibration(context)
        
        // 延迟测试其他振动类型
        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
            HapticFeedbackHelper.successVibration(context)
        }, 1000)
        
        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
            HapticFeedbackHelper.errorVibration(context)
        }, 2000)
        
        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
            HapticFeedbackHelper.errorVibration(context)
        }, 3000)
        
        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
            HapticFeedbackHelper.strongVibration(context)
        }, 4000)
        
        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
            HapticFeedbackHelper.doubleVibration(context)
        }, 5000)
    }
    
    /**
     * 测试所有通知类型
     */
    fun testAllNotifications(context: Context) {
        if (!NotificationHelper.hasNotificationPermission(context)) {
            Toast.makeText(context, "没有通知权限", Toast.LENGTH_SHORT).show()
            return
        }
        
        if (!SettingsManager.notificationsEnabled.value) {
            Toast.makeText(context, "通知功能已禁用", Toast.LENGTH_SHORT).show()
            return
        }
        
        // 测试简单通知
        NotificationHelper.sendSimpleNotification(context, "测试通知", "这是一个简单的测试通知")
        
        // 延迟测试其他通知类型
        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
            NotificationHelper.sendToolUsageNotification(context, "计算器")
        }, 2000)
        
        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
            NotificationHelper.sendTaskCompletedNotification(context, "测试任务")
        }, 4000)
    }
    
    /**
     * 测试组合反馈（振动+通知）
     */
    fun testCombinedFeedback(context: Context) {
        // 按钮点击反馈
        if (SettingsManager.hapticFeedbackEnabled.value && HapticFeedbackHelper.isVibrationSupported(context)) {
            HapticFeedbackHelper.buttonClickVibration(context)
        }
        
        // 成功操作反馈
        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
            if (SettingsManager.hapticFeedbackEnabled.value && HapticFeedbackHelper.isVibrationSupported(context)) {
                HapticFeedbackHelper.successVibration(context)
            }
            
            if (SettingsManager.notificationsEnabled.value && NotificationHelper.hasNotificationPermission(context)) {
                NotificationHelper.sendTaskCompletedNotification(context, "组合测试")
            }
        }, 1000)
    }
}