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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolsScreen(navController: NavController) {
    var selectedCategory by remember { mutableStateOf("全部") }
    var showFilterDialog by remember { mutableStateOf(false) }
    
    val categories = listOf("全部") + ToolsRepository.getToolsByCategory().keys.toList()
    val filteredTools = if (selectedCategory == "全部") {
        ToolsRepository.getAllTools()
    } else {
        ToolsRepository.getToolsByCategory()[selectedCategory] ?: emptyList()
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
                label = { Text(selectedCategory) },
                selected = selectedCategory != "全部",
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
            items(filteredTools) { tool ->
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
                    items(categories) { category ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedCategory == category,
                                onClick = {
                                    selectedCategory = category
                                    showFilterDialog = false
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(category)
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