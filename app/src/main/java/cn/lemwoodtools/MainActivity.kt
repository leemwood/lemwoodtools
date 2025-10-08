package cn.lemwoodtools

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import cn.lemwoodtools.ui.theme.LemwoodToolsTheme
import cn.lemwoodtools.ui.theme.LemwoodToolsThemeWithManager
import cn.lemwoodtools.ui.theme.ThemeMode
import cn.lemwoodtools.ui.theme.currentThemeMode
import cn.lemwoodtools.ui.theme.rememberThemeManager
import androidx.compose.material3.ExperimentalMaterial3Api

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemwoodToolsThemeWithManager {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ToolboxApp()
                }
            }
        }
    }
}

// 定义导航项
sealed class NavigationItem(val route: String, val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    object Tools : NavigationItem("tools", "工具", Icons.Filled.Build)
    object Settings : NavigationItem("settings", "设置", Icons.Filled.Settings)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolboxApp() {
    var selectedItem by remember { mutableStateOf(0) }
    val navigationItems = listOf(
        NavigationItem.Tools,
        NavigationItem.Settings
    )
    
    Scaffold(
        bottomBar = {
            NavigationBar {
                navigationItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title
                            )
                        },
                        label = {
                            Text(text = item.title)
                        },
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedItem) {
                0 -> ToolsScreen()
                1 -> SettingsScreen()
            }
        }
    }
}

@Composable
fun ToolsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Filled.Build,
            contentDescription = "工具箱图标",
            modifier = Modifier.size(120.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = stringResource(R.string.welcome_message),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = stringResource(R.string.no_tools_available),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.outline
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {
    val scrollState = rememberScrollState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        // 主题设置部分
        SettingsSection(title = "主题设置", icon = Icons.Filled.Info) {
            ThemeSettings()
        }
        
        Divider(modifier = Modifier.padding(horizontal = 16.dp))
        
        // 应用信息部分
        SettingsSection(title = "应用信息", icon = Icons.Filled.Info) {
            AppInfoSettings()
        }
    }
}

@Composable
fun SettingsSection(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        content()
    }
}

@Composable
fun ThemeSettings() {
    val currentThemeMode = currentThemeMode()
    val themeManager = rememberThemeManager()
    
    // 处理主题模式更改
    var selectedThemeMode by remember { mutableStateOf(currentThemeMode) }
    
    LaunchedEffect(selectedThemeMode) {
        if (selectedThemeMode != currentThemeMode) {
            themeManager.setThemeMode(selectedThemeMode)
        }
    }
    
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // 主题模式选择
        Text(
            text = "主题模式",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // 浅色模式选项
        ThemeOptionItem(
            title = "浅色模式",
            description = "始终使用浅色主题",
            isSelected = selectedThemeMode == ThemeMode.LIGHT,
            onClick = { selectedThemeMode = ThemeMode.LIGHT }
        )
        
        // 深色模式选项
        ThemeOptionItem(
            title = "深色模式", 
            description = "始终使用深色主题",
            isSelected = selectedThemeMode == ThemeMode.DARK,
            onClick = { selectedThemeMode = ThemeMode.DARK }
        )
        
        // 跟随系统选项
        ThemeOptionItem(
            title = "跟随系统",
            description = "根据系统设置自动切换主题",
            isSelected = selectedThemeMode == ThemeMode.SYSTEM,
            onClick = { selectedThemeMode = ThemeMode.SYSTEM }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // 主题预览
        Text(
            text = "主题预览",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        
        ThemePreviewSection()
    }
}

@Composable
fun ThemeOptionItem(
    title: String,
    description: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = MaterialTheme.shapes.medium,
        color = if (isSelected) MaterialTheme.colorScheme.primaryContainer 
               else MaterialTheme.colorScheme.surfaceVariant,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 选择指示器
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .background(
                        color = if (isSelected) MaterialTheme.colorScheme.primary 
                               else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                        shape = androidx.compose.foundation.shape.CircleShape
                    )
            ) {
                if (isSelected) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "已选择",
                        modifier = Modifier.size(12.dp),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer 
                           else MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Medium
                )
                
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                           else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun ThemePreviewSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // 浅色主题预览
        Box(
            modifier = Modifier.weight(1f)
        ) {
            ThemePreviewCard(
                title = "浅色",
                backgroundColor = Color(0xFFFFFBFF),
                primaryColor = Color(0xFFF9A825),
                textColor = Color(0xFF1B1B1F)
            )
        }
        
        // 深色主题预览
        Box(
            modifier = Modifier.weight(1f)
        ) {
            ThemePreviewCard(
                title = "深色", 
                backgroundColor = Color(0xFF1B1B1F),
                primaryColor = Color(0xFFFFB300),
                textColor = Color(0xFFE4E1E6)
            )
        }
    }
}

@Composable
fun ThemePreviewCard(
    title: String,
    backgroundColor: Color,
    primaryColor: Color,
    textColor: Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.8f)
            .background(backgroundColor, MaterialTheme.shapes.medium)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                shape = MaterialTheme.shapes.medium
            )
            .padding(12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                color = textColor,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold
            )
            
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(primaryColor, androidx.compose.foundation.shape.CircleShape)
            )
            
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .background(textColor.copy(alpha = 0.3f))
            )
            
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .background(textColor.copy(alpha = 0.2f))
            )
        }
    }
}

@Composable
fun AppInfoSettings() {
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current
    
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // 应用基本信息
        InfoItem(label = "应用名称", value = stringResource(R.string.app_name))
        InfoItem(label = "版本", value = "1.0.0")
        InfoItem(label = "包名", value = "cn.lemwoodtools")
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // GitHub链接
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surfaceVariant,
            onClick = {
                uriHandler.openUri("https://github.com/leemwood/leemwoodtools")
            }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = "GitHub仓库",
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "GitHub仓库",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    
                    Text(
                        text = "leemwood/leemwoodtools",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = "打开链接",
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.outline
                )
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // 开发信息
        Text(
            text = "开发信息",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        Text(
            text = "柠枺工具箱是一个实用的Android工具集合应用，提供多种便捷功能。",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun InfoItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ToolboxAppPreview() {
    LemwoodToolsThemeWithManager {
        ToolboxApp()
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    LemwoodToolsThemeWithManager {
        SettingsScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun ThemeSettingsPreview() {
    LemwoodToolsThemeWithManager {
        Surface(modifier = Modifier.fillMaxSize()) {
            ThemeSettings()
        }
    }
}