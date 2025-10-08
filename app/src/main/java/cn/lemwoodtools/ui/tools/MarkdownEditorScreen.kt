package cn.lemwoodtools.ui.tools

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.CreateNewFolder
import androidx.compose.material.icons.filled.ViewSidebar
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Preview
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Badge
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import kotlinx.coroutines.launch

/**
 * 编辑器模式枚举
 */
enum class EditorMode {
    SPLIT,    // 分屏模式（编辑+预览）
    EDIT,     // 完全编辑模式
    PREVIEW   // 完全预览模式
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarkdownEditorScreen(onBackClick: () -> Unit) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    
    // 状态管理
    var markdownText by remember { mutableStateOf("# Markdown编辑器\n\n欢迎使用柠枺工具箱的Markdown编辑器！\n\n## 功能特性\n\n- ✏️ **实时编辑** - 左侧编辑，右侧预览\n- 👀 **实时预览** - 所见即所得\n- 📁 **文件操作** - 导入、导出、重命名\n- 🎨 **语法高亮** - 支持Markdown语法\n- 📂 **文件管理** - 完整的工作区文件管理\n- 💾 **自动保存** - 支持自动保存功能\n\n## 使用方法\n\n1. 在左侧编辑区编写Markdown内容\n2. 右侧会自动显示预览效果\n3. 使用工具栏进行文件操作\n4. 支持文件导入导出和分享\n\n```javascript\n// 代码块示例\nfunction hello() {\n    console.log(\"Hello, Markdown!\");\n}\n```") }
    var fileName by remember { mutableStateOf("未命名文档.md") }
    var lastSavedContent by remember { mutableStateOf(markdownText) }
    var editorMode by remember { mutableStateOf(EditorMode.SPLIT) }
    var hasUnsavedChanges by remember { mutableStateOf(false) }
    var showSaveDialog by remember { mutableStateOf(false) }
    var showFileManager by remember { mutableStateOf(false) }
    var showImportExport by remember { mutableStateOf(false) }
    var autoSaveEnabled by remember { mutableStateOf(true) }
    
    // 自动保存逻辑
    LaunchedEffect(markdownText) {
        if (autoSaveEnabled && markdownText != lastSavedContent) {
            hasUnsavedChanges = true
            kotlinx.coroutines.delay(2000) // 2秒后自动保存
            if (hasUnsavedChanges) {
                coroutineScope.launch {
                    if (FileManager.saveMarkdownFile(context, fileName, markdownText)) {
                        lastSavedContent = markdownText
                        hasUnsavedChanges = false
                    }
                }
            }
        }
    }
    
    Column(modifier = Modifier.fillMaxSize()) {
        // 增强工具栏
        EnhancedToolbar(
            fileName = fileName,
            onFileNameChange = { newName -> fileName = newName },
            editorMode = editorMode,
            onEditorModeChange = { newMode -> editorMode = newMode },
            onSaveClick = { showSaveDialog = true },
            onFileManagerClick = { showFileManager = true },
            onImportExportClick = { showImportExport = true },
            onBackClick = onBackClick,
            hasUnsavedChanges = hasUnsavedChanges
        )
        
        // 编辑器内容区域
        when (editorMode) {
            EditorMode.SPLIT -> {
                // 分屏模式
                Row(modifier = Modifier.fillMaxSize()) {
                    // 编辑区域
                    EditorArea(
                        markdownText = markdownText,
                        onMarkdownTextChange = { newText -> markdownText = newText },
                        modifier = Modifier.weight(1f)
                    )
                    
                    // 预览区域
                    PreviewArea(
                        markdownText = markdownText,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            EditorMode.EDIT -> {
                // 完全编辑模式
                EditorArea(
                    markdownText = markdownText,
                    onMarkdownTextChange = { newText -> markdownText = newText },
                    modifier = Modifier.fillMaxSize()
                )
            }
            EditorMode.PREVIEW -> {
                // 完全预览模式
                PreviewArea(
                    markdownText = markdownText,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
    
    // 文件管理对话框
        if (showFileManager) {
            FileManagerDialog(
                onFileSelected = { selectedFileName, content ->
                    fileName = selectedFileName
                    markdownText = content
                    lastSavedContent = content
                    hasUnsavedChanges = false
                    showFileManager = false
                },
                onDismiss = { showFileManager = false }
            )
        }
        
        // 保存对话框
        if (showSaveDialog) {
            SaveFileDialog(
                fileName = fileName,
                onFileNameChange = { newName -> fileName = newName },
                onSave = { 
                    coroutineScope.launch {
                        FileManager.saveMarkdownFile(context, fileName, markdownText)
                        lastSavedContent = markdownText
                        hasUnsavedChanges = false
                        showSaveDialog = false
                    }
                },
                onCancel = { showSaveDialog = false }
            )
        }
        
        // 导入导出对话框
        if (showImportExport) {
            ImportExportManager(
                fileName = fileName,
                content = markdownText,
                onImport = { newName, content ->
                    fileName = newName
                    markdownText = content
                    lastSavedContent = content
                    hasUnsavedChanges = false
                    showImportExport = false
                },
                onExport = { 
                    // 导出完成后关闭对话框
                    showImportExport = false
                },
                onShare = {
                    FileManager.shareFile(context, fileName, markdownText)
                    showImportExport = false
                }
            )
        }
}

/**
 * 保存文件对话框
 */
@Composable
fun SaveFileDialog(
    fileName: String,
    onFileNameChange: (String) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text("保存文件") },
        text = {
            Column {
                OutlinedTextField(
                    value = fileName,
                    onValueChange = onFileNameChange,
                    label = { Text("文件名") },
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "文件将保存到工作区文件夹",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onSave) {
                Text("保存")
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text("取消")
            }
        }
    )
}

/**
 * 编辑区域
 */
@Composable
fun EditorArea(
    markdownText: String,
    onMarkdownTextChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // 标题栏
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "编辑区",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            
            // 编辑框
            BasicTextField(
                value = markdownText,
                onValueChange = onMarkdownTextChange,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            )
        }
    }
}

/**
 * 预览区域
 */
@Composable
fun PreviewArea(
    markdownText: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // 标题栏
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "预览区",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            
            // 预览内容
            MarkdownPreview(
                markdownText = markdownText,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

/**
 * 增强工具栏
 */
@Composable
fun EnhancedToolbar(
    fileName: String,
    onFileNameChange: (String) -> Unit,
    editorMode: EditorMode,
    onEditorModeChange: (EditorMode) -> Unit,
    onSaveClick: () -> Unit,
    onFileManagerClick: () -> Unit,
    onImportExportClick: () -> Unit,
    onBackClick: () -> Unit,
    hasUnsavedChanges: Boolean = false
) {
    var showRenameDialog by remember { mutableStateOf(false) }
    var showMoreMenu by remember { mutableStateOf(false) }
    
    // 使用简单的Box布局替代TopAppBar
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 返回按钮
            IconButton(onClick = onBackClick) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "返回", tint = MaterialTheme.colorScheme.onPrimary)
            }
            
            // 标题（显示文件名和保存状态）
            Column(
                modifier = Modifier.weight(1f).padding(horizontal = 16.dp)
            ) {
                Text(
                    text = fileName,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodyLarge
                )
                if (hasUnsavedChanges) {
                    Text(
                        text = "未保存",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                    )
                }
            }
            
            // 模式切换按钮
            IconButton(onClick = { 
                onEditorModeChange(when (editorMode) {
                    EditorMode.SPLIT -> EditorMode.EDIT
                    EditorMode.EDIT -> EditorMode.PREVIEW
                    EditorMode.PREVIEW -> EditorMode.SPLIT
                })
            }) {
                Icon(
                    imageVector = when (editorMode) {
                        EditorMode.SPLIT -> Icons.Filled.ViewSidebar
                        EditorMode.EDIT -> Icons.Filled.EditNote
                        EditorMode.PREVIEW -> Icons.Filled.Preview
                    },
                    contentDescription = when (editorMode) {
                        EditorMode.SPLIT -> "切换到编辑模式"
                        EditorMode.EDIT -> "切换到预览模式"
                        EditorMode.PREVIEW -> "切换到分屏模式"
                    },
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            
            // 文件管理按钮
            IconButton(onClick = onFileManagerClick) {
                Icon(Icons.Filled.Menu, "文件管理", tint = MaterialTheme.colorScheme.onPrimary)
            }
            
            // 保存按钮（带状态指示）
            IconButton(onClick = onSaveClick) {
                if (hasUnsavedChanges) {
                    @OptIn(ExperimentalMaterial3Api::class)
                    BadgedBox(
                        badge = { Badge(containerColor = MaterialTheme.colorScheme.error) }
                    ) {
                        Icon(Icons.Filled.Menu, "保存文件", tint = MaterialTheme.colorScheme.onPrimary)
                    }
                } else {
                    Icon(Icons.Filled.Menu, "保存文件", tint = MaterialTheme.colorScheme.onPrimary)
                }
            }
            
            // 更多操作菜单
            Box {
                IconButton(onClick = { showMoreMenu = true }) {
                    Icon(Icons.Filled.MoreVert, "更多操作", tint = MaterialTheme.colorScheme.onPrimary)
                }
                
                DropdownMenu(
                    expanded = showMoreMenu,
                    onDismissRequest = { showMoreMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("导入/导出") },
                        onClick = {
                            showMoreMenu = false
                            onImportExportClick()
                        },
                        leadingIcon = {
                            Icon(Icons.Filled.Menu, contentDescription = null)
                        }
                    )
                    
                    DropdownMenuItem(
                        text = { Text("重命名") },
                        onClick = {
                            showMoreMenu = false
                            showRenameDialog = true
                        },
                        leadingIcon = {
                            Icon(Icons.Filled.Edit, contentDescription = null)
                        }
                    )
                }
            }
        }
    }
    
    // 重命名对话框
    if (showRenameDialog) {
        AlertDialog(
            onDismissRequest = { showRenameDialog = false },
            title = { Text("重命名文件") },
            text = {
                OutlinedTextField(
                    value = fileName,
                    onValueChange = onFileNameChange,
                    label = { Text("文件名") },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(onClick = { showRenameDialog = false }) {
                    Text("确定")
                }
            },
            dismissButton = {
                TextButton(onClick = { showRenameDialog = false }) {
                    Text("取消")
                }
            }
        )
    }


/**
 * 打开文件对话框
 */
@Composable
fun OpenFileDialog(
    files: List<String>,
    onFileSelected: (String) -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text("打开文件") },
        text = {
            Column(modifier = Modifier.height(200.dp)) {
                Text(
                    text = "选择要打开的文件:",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                if (files.isEmpty()) {
                    Text(
                        text = "工作区暂无文件",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    LazyColumn {
                        items(files) { file ->
                            ListItem(
                                headlineContent = { Text(file) },
                                modifier = Modifier.clickable { onFileSelected(file) }
                            )
                            Divider()
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onCancel) {
                Text("关闭")
            }
        }
    )
}
}