package cn.lemwood.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cn.lemwood.R
import cn.lemwood.utils.FeedbackTestHelper
import cn.lemwood.utils.FeedbackUtils
import cn.lemwood.utils.HapticFeedbackHelper
import cn.lemwood.utils.LanguageManager
import cn.lemwood.utils.NotificationHelper
import cn.lemwood.utils.SettingsManager
import cn.lemwood.utils.ThemeManager
import cn.lemwood.utils.rememberCurrentLanguage
import cn.lemwood.utils.rememberHapticFeedbackEnabled
import cn.lemwood.utils.rememberIsDarkTheme
import cn.lemwood.utils.rememberNotificationsEnabled
import android.widget.Toast
import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {
    val context = LocalContext.current
    val currentLanguage = rememberCurrentLanguage()
    val isDarkTheme = rememberIsDarkTheme()
    val enableNotifications = rememberNotificationsEnabled()
    val enableHapticFeedback = rememberHapticFeedbackEnabled()
    var showLanguageDialog by remember { mutableStateOf(false) }
    
    // 通知权限请求
    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            SettingsManager.setNotificationsEnabled(true)
            Toast.makeText(context, "通知权限已授予", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "通知权限被拒绝", Toast.LENGTH_SHORT).show()
        }
    }
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                stringResource(R.string.settings),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        // 语言设置
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                onClick = { 
                    showLanguageDialog = true
                    if (enableHapticFeedback && HapticFeedbackHelper.isVibrationSupported(context)) {
                        HapticFeedbackHelper.buttonClickVibration(context)
                    }
                }
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        stringResource(R.string.language),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                currentLanguage.displayName,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                stringResource(R.string.language_setting_desc),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Icon(
                            Icons.Default.Language,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
        
        // 外观设置
        item {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        stringResource(R.string.appearance),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                stringResource(R.string.dark_theme),
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                stringResource(R.string.dark_theme_desc),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Switch(
                            checked = isDarkTheme,
                            onCheckedChange = { enabled ->
                                ThemeManager.setDarkTheme(enabled)
                                if (enableHapticFeedback && HapticFeedbackHelper.isVibrationSupported(context)) {
                                    HapticFeedbackHelper.buttonClickVibration(context)
                                }
                            }
                        )
                    }
                }
            }
        }
        
        // 通知设置
        item {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        stringResource(R.string.notifications),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                stringResource(R.string.push_notifications),
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                stringResource(R.string.push_notifications_desc),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Switch(
                            checked = enableNotifications,
                            onCheckedChange = { enabled ->
                                if (enabled) {
                                    // 检查通知权限
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                        if (NotificationHelper.hasNotificationPermission(context)) {
                                            SettingsManager.setNotificationsEnabled(true)
                                        } else {
                                            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                                        }
                                    } else {
                                        SettingsManager.setNotificationsEnabled(true)
                                    }
                                } else {
                                    SettingsManager.setNotificationsEnabled(false)
                                }
                            }
                        )
                    }
                }
            }
        }
        
        // 交互设置
        item {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        stringResource(R.string.interaction),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                stringResource(R.string.haptic_feedback),
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                stringResource(R.string.haptic_feedback_desc),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Switch(
                            checked = enableHapticFeedback,
                            onCheckedChange = { enabled ->
                                SettingsManager.setHapticFeedbackEnabled(enabled)
                                if (enabled && HapticFeedbackHelper.isVibrationSupported(context)) {
                                    HapticFeedbackHelper.successVibration(context)
                                }
                            }
                        )
                    }
                }
            }
        }
        
        // 关于应用
        item {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        stringResource(R.string.about),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    // 版本信息
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Info,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                stringResource(R.string.version),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        Text(
                            "1.0.0",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    
                    Divider()
                    
                    // 开发者信息
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                stringResource(R.string.developer),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        Text(
                            "Lemwood",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
        
        // 操作按钮
        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { 
                        // 按钮点击振动反馈
                        if (enableHapticFeedback && HapticFeedbackHelper.isVibrationSupported(context)) {
                            HapticFeedbackHelper.buttonClickVibration(context)
                        }
                        
                        val success = SettingsManager.clearCache(context)
                        val message = if (success) {
                            context.getString(R.string.cache_cleared)
                        } else {
                            context.getString(R.string.cache_clear_error)
                        }
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        
                        // 操作完成反馈
                        if (success) {
                            if (enableHapticFeedback && HapticFeedbackHelper.isVibrationSupported(context)) {
                                HapticFeedbackHelper.successVibration(context)
                            }
                            if (enableNotifications && NotificationHelper.hasNotificationPermission(context)) {
                                NotificationHelper.sendTaskCompletionNotification(context, "缓存清理", "缓存已成功清理")
                            }
                        } else {
                            if (enableHapticFeedback && HapticFeedbackHelper.isVibrationSupported(context)) {
                                HapticFeedbackHelper.errorVibration(context)
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        Icons.Default.CleaningServices,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(stringResource(R.string.clear_cache))
                }
                
                OutlinedButton(
                    onClick = { 
                        // 按钮点击振动反馈
                        if (enableHapticFeedback && HapticFeedbackHelper.isVibrationSupported(context)) {
                            HapticFeedbackHelper.buttonClickVibration(context)
                        }
                        
                        FeedbackUtils.sendEmailFeedback(context)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        Icons.Default.Feedback,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(stringResource(R.string.feedback))
                }
                
                // 测试反馈功能按钮
                OutlinedButton(
                    onClick = { 
                        // 按钮点击振动反馈
                        if (enableHapticFeedback && HapticFeedbackHelper.isVibrationSupported(context)) {
                            HapticFeedbackHelper.buttonClickVibration(context)
                        }
                        
                        // 测试组合反馈功能
                        FeedbackTestHelper.testCombinedFeedback(context)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        Icons.Default.BugReport,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("测试反馈功能")
                }
            }
        }
    }
    
    // 语言选择对话框
    if (showLanguageDialog) {
        LanguageSelectionDialog(
            currentLanguage = currentLanguage,
            onLanguageSelected = { language ->
                LanguageManager.setLanguage(context, language)
                showLanguageDialog = false
            },
            onDismiss = { showLanguageDialog = false }
        )
    }
}

/**
 * 语言选择对话框
 */
@Composable
private fun LanguageSelectionDialog(
    currentLanguage: LanguageManager.Language,
    onLanguageSelected: (LanguageManager.Language) -> Unit,
    onDismiss: () -> Unit
) {
    val supportedLanguages = LanguageManager.getSupportedLanguages()
    val context = LocalContext.current
    val enableHapticFeedback by rememberHapticFeedbackEnabled()
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(R.string.language),
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            Column(
                modifier = Modifier.selectableGroup()
            ) {
                supportedLanguages.forEach { language ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (language == currentLanguage),
                                onClick = { 
                                    if (enableHapticFeedback && HapticFeedbackHelper.isVibrationSupported(context)) {
                                        HapticFeedbackHelper.buttonClickVibration(context)
                                    }
                                    onLanguageSelected(language) 
                                },
                                role = Role.RadioButton
                            )
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (language == currentLanguage),
                            onClick = null
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = language.displayName,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = when (language) {
                                    LanguageManager.Language.CHINESE -> stringResource(R.string.language_chinese)
                                    LanguageManager.Language.ENGLISH -> stringResource(R.string.language_english)
                                },
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}