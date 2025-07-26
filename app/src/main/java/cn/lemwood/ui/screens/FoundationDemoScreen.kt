package cn.lemwood.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cn.lemwood.data.ToolItem
import cn.lemwood.ui.components.ToolCardFoundation
import cn.lemwood.ui.foundation.components.*
import cn.lemwood.ui.foundation.theme.FoundationTheme

@Composable
fun FoundationDemoScreen(
    modifier: Modifier = Modifier
) {
    // 示例工具数据
    val sampleTools = remember {
        listOf(
            ToolItem(
                id = "demo1",
                title = "Foundation按钮演示",
                description = "展示Foundation设计系统中的按钮组件",
                icon = Icons.Default.TouchApp,
                category = "demo"
            ),
            ToolItem(
                id = "demo2", 
                title = "Foundation卡片演示",
                description = "展示Foundation设计系统中的卡片组件",
                icon = Icons.Default.CreditCard,
                category = "demo"
            ),
            ToolItem(
                id = "demo3",
                title = "Foundation文本演示", 
                description = "展示Foundation设计系统中的文本组件",
                icon = Icons.Default.TextFields,
                category = "demo"
            )
        )
    }
    
    // 状态变量
    var switchChecked by remember { mutableStateOf(false) }
    var checkboxChecked by remember { mutableStateOf(false) }
    var textFieldValue by remember { mutableStateOf("") }
    
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            // 标题卡片
            FoundationCard(
                modifier = Modifier.fillMaxWidth(),
                elevation = 6.dp
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    FoundationText(
                        text = "Foundation UI 演示",
                        style = FoundationTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = FoundationTheme.colors.primary
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    FoundationText(
                        text = "轻量级Compose Foundation设计系统",
                        style = FoundationTheme.typography.bodyLarge,
                        color = FoundationTheme.colors.onSurfaceVariant
                    )
                }
            }
        }
        
        item {
            // 按钮演示卡片
            FoundationCard(
                modifier = Modifier.fillMaxWidth(),
                elevation = 4.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    FoundationText(
                        text = "按钮组件演示",
                        style = FoundationTheme.typography.titleLarge,
                        fontWeight = FontWeight.Medium,
                        color = FoundationTheme.colors.onSurface
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        FoundationButton(
                            onClick = { },
                            modifier = Modifier.weight(1f)
                        ) {
                            FoundationText(
                                text = "主要按钮",
                                color = FoundationTheme.colors.onPrimary
                            )
                        }
                        
                        FoundationButton(
                            onClick = { },
                            enabled = false,
                            modifier = Modifier.weight(1f)
                        ) {
                            FoundationText(
                                text = "禁用按钮",
                                color = FoundationTheme.colors.onSurface.copy(alpha = 0.38f)
                            )
                        }
                    }
                }
            }
        }
        
        item {
            // 图标按钮演示
            FoundationCard(
                modifier = Modifier.fillMaxWidth(),
                elevation = 4.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    FoundationText(
                        text = "图标按钮演示",
                        style = FoundationTheme.typography.titleLarge,
                        fontWeight = FontWeight.Medium,
                        color = FoundationTheme.colors.onSurface
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        FoundationIconButton(
                            onClick = { }
                        ) {
                            Icon(
                                Icons.Default.Favorite,
                                contentDescription = "喜欢",
                                tint = FoundationTheme.colors.primary
                            )
                        }
                        
                        FoundationFilledIconButton(
                            onClick = { }
                        ) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = "添加"
                            )
                        }
                        
                        FoundationOutlinedIconButton(
                            onClick = { }
                        ) {
                            Icon(
                                Icons.Default.Settings,
                                contentDescription = "设置"
                            )
                        }
                        
                        FoundationSmallFloatingActionButton(
                            onClick = { }
                        ) {
                            Icon(
                                Icons.Default.Edit,
                                contentDescription = "编辑"
                            )
                        }
                    }
                }
            }
        }
        
        item {
            // 输入控件演示
            FoundationCard(
                modifier = Modifier.fillMaxWidth(),
                elevation = 4.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    FoundationText(
                        text = "输入控件演示",
                        style = FoundationTheme.typography.titleLarge,
                        fontWeight = FontWeight.Medium,
                        color = FoundationTheme.colors.onSurface
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    FoundationTextField(
                        value = textFieldValue,
                        onValueChange = { textFieldValue = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            FoundationText(
                                text = "请输入文本...",
                                color = FoundationTheme.colors.onSurfaceVariant
                            )
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = "搜索",
                                tint = FoundationTheme.colors.onSurfaceVariant
                            )
                        }
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            FoundationSwitch(
                                checked = switchChecked,
                                onCheckedChange = { switchChecked = it }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            FoundationText(
                                text = "开关",
                                style = FoundationTheme.typography.bodyMedium
                            )
                        }
                        
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            FoundationCheckbox(
                                checked = checkboxChecked,
                                onCheckedChange = { checkboxChecked = it }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            FoundationText(
                                text = "复选框",
                                style = FoundationTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
        
        item {
            // 文本样式演示
            FoundationCard(
                modifier = Modifier.fillMaxWidth(),
                elevation = 4.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    FoundationText(
                        text = "文本样式演示",
                        style = FoundationTheme.typography.titleLarge,
                        fontWeight = FontWeight.Medium,
                        color = FoundationTheme.colors.onSurface
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    FoundationText(
                        text = "Display Large",
                        style = FoundationTheme.typography.displayLarge,
                        color = FoundationTheme.colors.primary
                    )
                    
                    FoundationText(
                        text = "Headline Medium",
                        style = FoundationTheme.typography.headlineMedium,
                        color = FoundationTheme.colors.onSurface
                    )
                    
                    FoundationText(
                        text = "Title Medium",
                        style = FoundationTheme.typography.titleMedium,
                        color = FoundationTheme.colors.onSurface
                    )
                    
                    FoundationText(
                        text = "Body Large - 这是正文大号文本，用于主要内容显示",
                        style = FoundationTheme.typography.bodyLarge,
                        color = FoundationTheme.colors.onSurface
                    )
                    
                    FoundationText(
                        text = "Body Medium - 这是正文中号文本，用于一般内容显示",
                        style = FoundationTheme.typography.bodyMedium,
                        color = FoundationTheme.colors.onSurfaceVariant
                    )
                    
                    FoundationText(
                        text = "Label Small",
                        style = FoundationTheme.typography.labelSmall,
                        color = FoundationTheme.colors.secondary
                    )
                }
            }
        }
        
        item {
            FoundationText(
                text = "工具卡片演示",
                style = FoundationTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium,
                color = FoundationTheme.colors.onSurface
            )
        }
        
        // 工具卡片列表
        items(sampleTools) { tool ->
            ToolCardFoundation(
                tool = tool,
                onClick = { }
            )
        }
    }
}