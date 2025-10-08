package cn.lemwoodtools.ui.tools

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarkdownEditorScreen(onBackClick: () -> Unit) {
    var markdownText by remember { mutableStateOf("# Markdown编辑器\n\n欢迎使用Markdown编辑器！") }
    var fileName by remember { mutableStateOf("未命名文档.md") }
    var showPreview by remember { mutableStateOf(true) }
    
    Column(modifier = Modifier.fillMaxSize()) {
        // 简化工具栏
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "返回")
                }
            },
            title = { Text("Markdown编辑器") },
            actions = {
                IconButton(onClick = { /* 重命名 */ }) {
                    Icon(Icons.Filled.Build, "重命名")
                }
                IconButton(onClick = { /* 导入 */ }) {
                    Icon(Icons.Filled.Settings, "导入")
                }
                IconButton(onClick = { /* 导出 */ }) {
                    Icon(Icons.Filled.Info, "导出")
                }
                IconButton(onClick = { showPreview = !showPreview }) {
                    Icon(
                        if (showPreview) Icons.Filled.Close else Icons.Filled.Check,
                        if (showPreview) "隐藏预览" else "显示预览"
                    )
                }
            }
        )
        
        // 简化编辑和预览区域
        Row(modifier = Modifier.fillMaxSize()) {
            // 编辑区域
            Box(modifier = Modifier.weight(1f).fillMaxHeight()) {
                BasicTextField(
                    value = markdownText,
                    onValueChange = { newText -> markdownText = newText },
                    modifier = Modifier.fillMaxSize().padding(16.dp)
                )
            }
            
            // 预览区域
            if (showPreview) {
                Box(modifier = Modifier.weight(1f).fillMaxHeight()) {
                    MarkdownPreview(
                        markdownText = markdownText,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}