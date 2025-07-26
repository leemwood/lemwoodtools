package cn.lemwood.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cn.lemwood.R
import cn.lemwood.data.ToolsRepository
import cn.lemwood.ui.components.ToolCardFoundation
import cn.lemwood.ui.foundation.components.*
import cn.lemwood.ui.foundation.theme.FoundationTheme

@Composable
fun HomeScreenFoundation(navController: NavController) {
    // 缓存工具数据，避免重复获取
    val allTools = remember { ToolsRepository.getAllTools() }
    val featuredTools = remember { allTools.take(4) } // 取前4个作为精选工具
    val recentTools = remember { allTools.takeLast(3) } // 取后3个作为最近工具
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 欢迎文本
        item {
            Column {
                FoundationText(
                    text = stringResource(R.string.welcome_title),
                    style = FoundationTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = FoundationTheme.colors.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                FoundationText(
                    text = stringResource(R.string.welcome_subtitle),
                    style = FoundationTheme.typography.bodyLarge,
                    color = FoundationTheme.colors.onSurfaceVariant
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Foundation UI 演示按钮
                FoundationButton(
                    onClick = { navController.navigate("foundation_demo") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    FoundationText(
                        text = "查看 Foundation UI 演示",
                        color = FoundationTheme.colors.onPrimary
                    )
                }
            }
        }
        
        // 推荐工具部分
        if (featuredTools.isNotEmpty()) {
            item {
                FoundationText(
                    text = stringResource(R.string.featured_tools),
                    style = FoundationTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = FoundationTheme.colors.onSurface
                )
            }
            
            items(
                items = featuredTools,
                key = { it.id } // 添加key优化性能
            ) { tool ->
                ToolCardFoundation(
                    tool = tool,
                    onClick = { navController.navigate(tool.route) }
                )
            }
        }
        
        // 最近使用工具部分
        if (recentTools.isNotEmpty()) {
            item {
                FoundationText(
                    text = stringResource(R.string.recent_tools),
                    style = FoundationTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = FoundationTheme.colors.onSurface
                )
            }
            
            items(
                items = recentTools,
                key = { it.id } // 添加key优化性能
            ) { tool ->
                ToolCardFoundation(
                    tool = tool,
                    onClick = { navController.navigate(tool.route) }
                )
            }
        }
        
        // 欢迎卡片
        item {
            WelcomeCardFoundation()
        }
        
        // 统计卡片
        item {
            StatsCardFoundation()
        }
        
        // 底部间距
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun WelcomeCardFoundation() {
    FoundationCard(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = FoundationTheme.colors.primaryContainer,
        onClick = { /* 可以添加点击行为 */ }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                Icons.Default.Build,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = FoundationTheme.colors.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(8.dp))
            FoundationText(
                text = stringResource(R.string.welcome_title),
                style = FoundationTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = FoundationTheme.colors.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(4.dp))
            FoundationText(
                text = stringResource(R.string.welcome_subtitle),
                style = FoundationTheme.typography.bodyMedium,
                color = FoundationTheme.colors.onPrimaryContainer
            )
        }
    }
}

@Composable
fun StatsCardFoundation() {
    FoundationCard(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = FoundationTheme.colors.surfaceVariant
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            FoundationText(
                text = stringResource(R.string.app_stats),
                style = FoundationTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = FoundationTheme.colors.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatItemFoundation(
                    label = stringResource(R.string.total_tools),
                    value = ToolsRepository.getAllTools().size.toString()
                )
                StatItemFoundation(
                    label = stringResource(R.string.categories),
                    value = ToolsRepository.getToolsByCategory().size.toString()
                )
            }
        }
    }
}

@Composable
fun StatItemFoundation(label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FoundationText(
            text = value,
            style = FoundationTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = FoundationTheme.colors.primary
        )
        FoundationText(
            text = label,
            style = FoundationTheme.typography.bodySmall,
            color = FoundationTheme.colors.onSurfaceVariant
        )
    }
}