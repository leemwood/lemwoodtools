package cn.lemwood.utils

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import cn.lemwood.R

/**
 * 通知助手类
 * 负责管理应用的通知功能
 */
object NotificationHelper {
    
    private const val CHANNEL_ID = "lemwood_tools_channel"
    private const val CHANNEL_NAME = "LemwoodTools通知"
    private const val CHANNEL_DESCRIPTION = "LemwoodTools应用通知"
    
    /**
     * 初始化通知渠道
     */
    fun initNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
                description = CHANNEL_DESCRIPTION
            }
            
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    
    /**
     * 检查通知权限
     */
    fun hasNotificationPermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            NotificationManagerCompat.from(context).areNotificationsEnabled()
        }
    }
    
    /**
     * 发送简单通知
     */
    fun sendSimpleNotification(
        context: Context,
        title: String,
        content: String,
        notificationId: Int = 1
    ) {
        if (!SettingsManager.notificationsEnabled.value || !hasNotificationPermission(context)) {
            return
        }
        
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_simple)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
        
        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                notify(notificationId, builder.build())
            }
        }
    }
    
    /**
     * 发送工具使用通知
     */
    fun sendToolUsageNotification(context: Context, toolName: String) {
        sendSimpleNotification(
            context = context,
            title = context.getString(R.string.app_name),
            content = "已使用工具：$toolName"
        )
    }
    
    /**
     * 发送任务完成通知
     */
    fun sendTaskCompletedNotification(context: Context, taskName: String) {
        sendSimpleNotification(
            context = context,
            title = "任务完成",
            content = "$taskName 已完成"
        )
    }
}