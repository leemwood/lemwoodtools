package cn.lemwood.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
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
import cn.lemwood.utils.CategoryHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolsScreen(navController: NavController) {
    val allCategoryText = stringResource(R.string.category_all)
    var selectedCategoryKey by remember { mutableStateOf("") } // 使用空字符串表示"全部"
    var showFilterDialog by remember { mutableStateOf(false) }
    
    // 缓存分类映射，避免重复计算
    val categoryMap = remember { CategoryHelper.getCategoryMap() }
    val categories = remember(allCategoryText) { 
        listOf("" to allCategoryText) + categoryMap.toList() 
    }
    
    // 缓存所有工具列表，避免重复获取
    val allTools = remember { ToolsRepository.getAllTools() }
    val toolsByCategory = remember { ToolsRepository.getToolsByCategory() }
    
    // 使用derivedStateOf优化过滤逻辑
    val filteredTools by derivedStateOf {
        if (selectedCategoryKey.isEmpty()) {
            allTools
        } else {
            toolsByCategory[selectedCategoryKey] ?: emptyList()
        }
    }
    
    val selectedCategoryDisplayName = remember(selectedCategoryKey, allCategoryText) {
        if (selectedCategoryKey.isEmpty()) {
            allCategoryText
        } else {
            CategoryHelper.getLocalizedCategoryName(selectedCategoryKey)
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 标题和筛选按钮
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                stringResource(R.string.all_tools),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            
            FilterChip(
                onClick = { showFilterDialog = true },
                label = { Text(selectedCategoryDisplayName) },
                selected = selectedCategoryKey.isNotEmpty(),
                leadingIcon = {
                    Icon(
                        Icons.Default.FilterList,
                        contentDescription = stringResource(R.string.filter)
                    )
                }
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // 工具列表
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = filteredTools,
                key = { it.id } // 添加key以优化重组性能
            ) { tool ->
                ToolCard(
                    tool = tool,
                    onClick = { navController.navigate(tool.route) }
                )
            }
        }
    }
    
    // 筛选对话框
    if (showFilterDialog) {
        AlertDialog(
            onDismissRequest = { showFilterDialog = false },
            title = { Text(stringResource(R.string.select_category)) },
            text = {
                LazyColumn {
                    items(
                        items = categories,
                        key = { it.first } // 添加key优化性能
                    ) { (categoryKey, categoryName) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedCategoryKey == categoryKey,
                                onClick = {
                                    selectedCategoryKey = categoryKey
                                    showFilterDialog = false
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(categoryName)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = { showFilterDialog = false }
                ) {
                    Text(stringResource(R.string.close))
                }
            }
        )
    }
}