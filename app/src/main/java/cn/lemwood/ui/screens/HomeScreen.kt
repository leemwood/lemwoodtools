package cn.lemwood.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cn.lemwood.R
import cn.lemwood.data.ToolsRepository
import cn.lemwood.ui.components.ToolCard

@Composable
fun HomeScreen(navController: NavController) {
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
                Text(
                    stringResource(R.string.welcome_title),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    stringResource(R.string.welcome_subtitle),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Foundation UI 演示按钮
                Button(
                    onClick = { navController.navigate("foundation_demo") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("查看 Foundation UI 演示")
                }
            }
        }
        
        // 推荐工具部分
        if (featuredTools.isNotEmpty()) {
            item {
                Text(
                    stringResource(R.string.featured_tools),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }
            
            items(
                items = featuredTools,
                key = { it.id } // 添加key优化性能
            ) { tool ->
                ToolCard(
                    tool = tool,
                    onClick = { navController.navigate(tool.route) }
                )
            }
        }
        
        // 最近使用工具部分
        if (recentTools.isNotEmpty()) {
            item {
                Text(
                    stringResource(R.string.recent_tools),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }
            
            items(
                items = recentTools,
                key = { it.id } // 添加key优化性能
            ) { tool ->
                ToolCard(
                    tool = tool,
                    onClick = { navController.navigate(tool.route) }
                )
            }
        }
        
        // 底部间距
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun WelcomeCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                Icons.Default.Build,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                stringResource(R.string.welcome_title),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                stringResource(R.string.welcome_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
fun StatsCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                stringResource(R.string.app_stats),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatItem(
                    label = stringResource(R.string.total_tools),
                    value = ToolsRepository.getAllTools().size.toString()
                )
                StatItem(
                    label = stringResource(R.string.categories),
                    value = ToolsRepository.getToolsByCategory().size.toString()
                )
            }
        }
    }
}

@Composable
fun StatItem(label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            value,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}