package cn.lemwood.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cn.lemwood.ui.foundation.components.FoundationCard
import cn.lemwood.ui.foundation.components.FoundationText
import cn.lemwood.ui.foundation.theme.FoundationTheme
import cn.lemwood.utils.NotificationHelper
import cn.lemwood.utils.HapticFeedbackHelper
import cn.lemwood.utils.rememberNotificationsEnabled
import cn.lemwood.utils.rememberHapticFeedbackEnabled

@Composable
fun PlaceholderScreenFoundation(toolName: String) {
    val context = LocalContext.current
    val notificationsEnabled = rememberNotificationsEnabled()
    val hapticFeedbackEnabled = rememberHapticFeedbackEnabled()
    
    // 进入工具时的反馈
    LaunchedEffect(toolName) {
        if (hapticFeedbackEnabled && HapticFeedbackHelper.isVibrationSupported(context)) {
            HapticFeedbackHelper.lightVibration(context)
        }
        
        if (notificationsEnabled && NotificationHelper.hasNotificationPermission(context)) {
            NotificationHelper.sendToolUsageNotification(context, toolName)
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Build,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = FoundationTheme.colors.primary
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        FoundationText(
            text = toolName,
            style = FoundationTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = FoundationTheme.colors.onBackground
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        FoundationText(
            text = "此工具正在开发中...",
            style = FoundationTheme.typography.bodyLarge,
            color = FoundationTheme.colors.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        FoundationCard(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = FoundationTheme.colors.surfaceVariant
        ) {
            FoundationText(
                text = "敬请期待",
                style = FoundationTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium,
                color = FoundationTheme.colors.onSurfaceVariant,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            FoundationText(
                text = "我们正在努力开发这个功能，请稍后再试。",
                style = FoundationTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = FoundationTheme.colors.onSurfaceVariant
            )
        }
    }
}