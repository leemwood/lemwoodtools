package cn.lemwood

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cn.lemwood.utils.LanguageManager
import cn.lemwood.utils.ThemeManager
import cn.lemwood.utils.SettingsManager
import cn.lemwood.utils.NotificationHelper

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // 初始化语言管理器
        LanguageManager.initialize(this)
        
        // 初始化主题管理器
        ThemeManager.initialize(this)
        
        // 初始化设置管理器
        SettingsManager.initialize(this)
        
        // 初始化通知渠道
        NotificationHelper.initNotificationChannel(this)
        
        setContent {
            LemwoodToolsApp()
        }
    }
}