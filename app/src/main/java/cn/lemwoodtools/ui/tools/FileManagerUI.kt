package cn.lemwoodtools.ui.tools

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import java.io.File
import java.util.Date

/**
 * 文件管理对话框
 */
@Composable
fun FileManagerDialog(
    onFileSelected: (String, String) -> Unit,
    onDismiss: () -> Unit,
    currentFileName: String = ""
) {
    val context = LocalContext.current
    var files by remember { mutableStateOf<List<FileInfo>>(emptyList()) }
    var selectedFile by remember { mutableStateOf<FileInfo?>(null) }
    var showDeleteConfirm by remember { mutableStateOf<FileInfo?>(null) }
    var showRenameDialog by remember { mutableStateOf<FileInfo?>(null) }
    var showNewFileDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    
    // 加载文件列表
    fun loadFiles() {
        coroutineScope.launch {
            files = FileManager.getMarkdownFiles(context)
        }
    }
    
    LaunchedEffect(Unit) {
        loadFiles()
    }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("文件管理") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            ) {
                // 工具栏
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "工作区文件 (${files.size})",
                        style = MaterialTheme.typography.titleSmall
                    )
                    
                    Row {
                        // 新建文件按钮
                        IconButton(
                            onClick = { showNewFileDialog = true },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "新建文件",
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        
                        // 刷新按钮
                        IconButton(
                            onClick = { loadFiles() },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "刷新",
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
                
                Divider()
                
                if (files.isEmpty()) {
                    // 空状态
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.Description,
                                contentDescription = "无文件",
                                modifier = Modifier.size(64.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "工作区暂无文件",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                } else {
                    // 文件列表
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(files) { file ->
                            FileItem(
                                fileInfo = file,
                                isSelected = file.name == currentFileName,
                                onClick = { selectedFile = file },
                                onDelete = { showDeleteConfirm = file },
                                onRename = { showRenameDialog = file }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Row {
                TextButton(onClick = onDismiss) {
                    Text("取消")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        selectedFile?.let {
                            val content = FileManager.loadMarkdownFile(context, it.name)
                            onFileSelected(it.name, content)
                        }
                    },
                    enabled = selectedFile != null
                ) {
                    Text("打开")
                }
            }
        }
    )
    
    // 删除确认对话框
    showDeleteConfirm?.let { file ->
        AlertDialog(
            onDismissRequest = { showDeleteConfirm = null },
            title = { Text("确认删除") },
            text = { Text("确定要删除文件 \"${file.name}\" 吗？") },
            confirmButton = {
                TextButton(
                    onClick = {
                        coroutineScope.launch {
                            if (FileManager.deleteFile(context, file.name)) {
                                loadFiles()
                                showDeleteConfirm = null
                            }
                        }
                    }
                ) {
                    Text("删除", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirm = null }) {
                    Text("取消")
                }
            }
        )
    }
    
    // 重命名对话框
    showRenameDialog?.let { file ->
        var newName by remember { mutableStateOf(file.name) }
        
        AlertDialog(
            onDismissRequest = { showRenameDialog = null },
            title = { Text("重命名文件") },
            text = {
                OutlinedTextField(
                    value = newName,
                    onValueChange = { newName = it },
                    label = { Text("文件名") },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        coroutineScope.launch {
                            if (FileManager.renameFile(context, file.name, newName)) {
                                loadFiles()
                                showRenameDialog = null
                            }
                        }
                    }
                ) {
                    Text("确定")
                }
            },
            dismissButton = {
                TextButton(onClick = { showRenameDialog = null }) {
                    Text("取消")
                }
            }
        )
    }
    
    // 新建文件对话框
    if (showNewFileDialog) {
        var newFileName by remember { mutableStateOf("新建文档.md") }
        
        AlertDialog(
            onDismissRequest = { showNewFileDialog = false },
            title = { Text("新建文件") },
            text = {
                OutlinedTextField(
                    value = newFileName,
                    onValueChange = { newFileName = it },
                    label = { Text("文件名") },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        coroutineScope.launch {
                            if (FileManager.createNewFile(context, newFileName)) {
                                loadFiles()
                                showNewFileDialog = false
                            }
                        }
                    }
                ) {
                    Text("创建")
                }
            },
            dismissButton = {
                TextButton(onClick = { showNewFileDialog = false }) {
                    Text("取消")
                }
            }
        )
    }
}

/**
 * 文件项组件
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FileItem(
    fileInfo: FileInfo,
    isSelected: Boolean,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    onRename: () -> Unit
) {
    val bgColor = if (isSelected) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.surface
    }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = bgColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 文件图标
            Icon(
                imageVector = Icons.Default.Description,
                contentDescription = "文件",
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            // 文件信息
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = fileInfo.name,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${FileManager.formatFileSize(fileInfo.size)} • ${FileManager.formatDate(fileInfo.lastModified)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            // 操作按钮
            Row {
                IconButton(
                    onClick = onRename,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "重命名",
                        modifier = Modifier.size(18.dp)
                    )
                }
                
                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "删除",
                        modifier = Modifier.size(18.dp),
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}