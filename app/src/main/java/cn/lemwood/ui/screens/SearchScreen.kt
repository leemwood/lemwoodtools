package cn.lemwood.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cn.lemwood.R
import cn.lemwood.data.ToolsRepository
import cn.lemwood.ui.components.ToolCard
import cn.lemwood.utils.CategoryHelper
import cn.lemwood.utils.HapticFeedbackHelper
import cn.lemwood.utils.rememberHapticFeedbackEnabled

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }
    val context = LocalContext.current
    val enableHapticFeedback = rememberHapticFeedbackEnabled()
    
    // 缓存振动支持检查结果
    val isVibrationSupported = remember {
        HapticFeedbackHelper.isVibrationSupported(context)
    }
    
    // 优化搜索结果计算，使用derivedStateOf减少重组
    val searchResults by remember {
        derivedStateOf {
            if (searchQuery.isBlank()) {
                emptyList()
            } else {
                ToolsRepository.searchTools(searchQuery)
            }
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 搜索框
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text(stringResource(R.string.search_tools)) },
            placeholder = { Text(stringResource(R.string.search_placeholder)) },
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = stringResource(R.string.search)
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(
                        onClick = { 
                            // 优化振动反馈调用
                            if (enableHapticFeedback && isVibrationSupported) {
                                HapticFeedbackHelper.buttonClickVibration(context)
                            }
                            searchQuery = "" 
                        }
                    ) {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = stringResource(R.string.clear)
                        )
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // 搜索结果
        when {
            searchQuery.isBlank() -> {
                // 空状态
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            stringResource(R.string.search_hint),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            searchResults.isEmpty() -> {
                // 无结果状态
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            stringResource(R.string.no_results),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            stringResource(R.string.try_different_keywords),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            else -> {
                // 搜索结果列表
                Column {
                    Text(
                        stringResource(R.string.search_results_count, searchResults.size),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = searchResults,
                            key = { it.id } // 添加key以优化重组性能
                        ) { tool ->
                            ToolCard(
                                tool = tool,
                                onClick = { navController.navigate(tool.route) }
                            )
                        }
                    }
                }
            }
        }
    }
}