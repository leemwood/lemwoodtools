package cn.lemwood.utils

import android.content.Context
import android.widget.Toast

/**
 * 反馈功能测试助手
 * 用于测试通知和振动反馈功能是否正常工作
 */
object FeedbackTestHelper {
    
    /**
     * 测试所有振动反馈类型
     */
    fun testAllVibrations(context: Context) {
        if (!HapticFeedbackHelper.isVibrationSupported(context)) {
            Toast.makeText(context, "设备不支持振动功能", Toast.LENGTH_SHORT).show()
            return
        }
        
        if (!SettingsManager.hapticFeedbackEnabled.value) {
            Toast.makeText(context, "振动反馈已禁用", Toast.LENGTH_SHORT).show()
            return
        }
        
        // 测试轻微振动
        HapticFeedbackHelper.lightVibration(context)
        Toast.makeText(context, "轻微振动测试", Toast.LENGTH_SHORT).show()
        
        // 延迟测试其他振动类型
        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
            HapticFeedbackHelper.mediumVibration(context)
            Toast.makeText(context, "中等振动测试", Toast.LENGTH_SHORT).show()
        }, 1000)
        
        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
            HapticFeedbackHelper.strongVibration(context)
            Toast.makeText(context, "强烈振动测试", Toast.LENGTH_SHORT).show()
        }, 2000)
        
        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
            HapticFeedbackHelper.successVibration(context)
            Toast.makeText(context, "成功振动测试", Toast.LENGTH_SHORT).show()
        }, 3000)
        
        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
            HapticFeedbackHelper.errorVibration(context)
            Toast.makeText(context, "错误振动测试", Toast.LENGTH_SHORT).show()
        }, 4000)
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
            NotificationHelper.sendToolUsageNotification(context, "计算器", "正在使用计算器工具")
        }, 2000)
        
        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
            NotificationHelper.sendTaskCompletionNotification(context, "测试任务", "任务已成功完成")
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
                NotificationHelper.sendTaskCompletionNotification(context, "组合测试", "振动和通知反馈测试完成")
            }
        }, 1000)
    }
}