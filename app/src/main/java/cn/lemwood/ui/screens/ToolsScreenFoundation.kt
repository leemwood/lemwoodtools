package cn.lemwood.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
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
import cn.lemwood.utils.CategoryHelper

@Composable
fun ToolsScreenFoundation(navController: NavController) {
    val allCategoryText = stringResource(R.string.category_all)
    var selectedCategoryKey by remember { mutableStateOf("") } // 使用空字符串表示"全部"
    var showFilterDialog by remember { mutableStateOf(false) }
    
    // 缓存分类映射，避免重复计算
    val categoryMap = CategoryHelper.getCategoryMap()
    val categories = listOf("" to allCategoryText) + categoryMap.toList()
    
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
            FoundationText(
                text = stringResource(R.string.all_tools),
                style = FoundationTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = FoundationTheme.colors.onSurface
            )
            
            // 使用Foundation按钮替代FilterChip
            FoundationOutlinedButton(
                onClick = { showFilterDialog = true },
                modifier = Modifier.wrapContentSize()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        Icons.Default.FilterList,
                        contentDescription = stringResource(R.string.filter),
                        tint = if (selectedCategoryKey.isNotEmpty()) 
                            FoundationTheme.colors.primary 
                        else 
                            FoundationTheme.colors.onSurface
                    )
                    val displayName = if (selectedCategoryKey.isEmpty()) {
                        allCategoryText
                    } else {
                        CategoryHelper.getLocalizedCategoryName(selectedCategoryKey)
                    }
                    FoundationText(
                        text = displayName,
                        color = if (selectedCategoryKey.isNotEmpty()) 
                            FoundationTheme.colors.primary 
                        else 
                            FoundationTheme.colors.onSurface
                    )
                }
            }
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
                ToolCardFoundation(
                    tool = tool,
                    onClick = { navController.navigate(tool.route) }
                )
            }
        }
    }
    
    // 筛选对话框
    if (showFilterDialog) {
        FoundationAlertDialog(
            onDismissRequest = { showFilterDialog = false },
            title = stringResource(R.string.select_category),
            content = {
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
                            FoundationRadioButton(
                                selected = selectedCategoryKey == categoryKey,
                                onClick = {
                                    selectedCategoryKey = categoryKey
                                    showFilterDialog = false
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            FoundationText(
                                text = categoryName,
                                color = FoundationTheme.colors.onSurface
                            )
                        }
                    }
                }
            },
            confirmButton = {
                FoundationTextButton(
                    onClick = { showFilterDialog = false }
                ) {
                    FoundationText(
                        text = stringResource(R.string.close),
                        color = FoundationTheme.colors.primary
                    )
                }
            }
        )
    }
}

@Composable
fun FoundationRadioButton(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FoundationIconButton(
        onClick = onClick,
        modifier = modifier.size(24.dp)
    ) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .background(
                    color = if (selected) FoundationTheme.colors.primary else FoundationTheme.colors.outline,
                    shape = FoundationTheme.shapes.circle
                ),
            contentAlignment = Alignment.Center
        ) {
            if (selected) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(
                            color = FoundationTheme.colors.onPrimary,
                            shape = FoundationTheme.shapes.circle
                        )
                )
            }
        }
    }
}