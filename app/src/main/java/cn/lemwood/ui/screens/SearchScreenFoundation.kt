package cn.lemwood.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cn.lemwood.R
import cn.lemwood.data.ToolsRepository
import cn.lemwood.ui.components.ToolCardFoundation
import cn.lemwood.ui.foundation.components.*
import cn.lemwood.ui.foundation.theme.FoundationTheme
import cn.lemwood.utils.HapticFeedbackHelper
import cn.lemwood.utils.rememberHapticFeedbackEnabled

@Composable
fun SearchScreenFoundation(navController: NavController) {
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
        FoundationOutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = stringResource(R.string.search_tools),
            placeholder = stringResource(R.string.search_placeholder),
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = stringResource(R.string.search),
                    tint = FoundationTheme.colors.onSurfaceVariant
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    FoundationIconButton(
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
                            contentDescription = stringResource(R.string.clear),
                            tint = FoundationTheme.colors.onSurfaceVariant
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
                            tint = FoundationTheme.colors.onSurfaceVariant.copy(alpha = 0.6f)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        FoundationText(
                            text = stringResource(R.string.search_hint),
                            style = FoundationTheme.typography.bodyLarge,
                            color = FoundationTheme.colors.onSurfaceVariant
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
                        FoundationText(
                            text = stringResource(R.string.no_results),
                            style = FoundationTheme.typography.titleMedium,
                            fontWeight = FontWeight.Medium,
                            color = FoundationTheme.colors.onSurface
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        FoundationText(
                            text = stringResource(R.string.try_different_keywords),
                            style = FoundationTheme.typography.bodyMedium,
                            color = FoundationTheme.colors.onSurfaceVariant
                        )
                    }
                }
            }
            else -> {
                // 搜索结果列表
                Column {
                    FoundationText(
                        text = stringResource(R.string.search_results_count, searchResults.size),
                        style = FoundationTheme.typography.bodyMedium,
                        color = FoundationTheme.colors.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = searchResults,
                            key = { it.id } // 添加key以优化重组性能
                        ) { tool ->
                            ToolCardFoundation(
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