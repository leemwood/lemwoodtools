package cn.lemwood

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import cn.lemwood.ui.theme.LemwoodToolsTheme
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
            LemwoodToolsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LemwoodToolsApp()
                }
            }
        }
    }
}