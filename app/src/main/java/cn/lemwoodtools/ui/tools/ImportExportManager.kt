package cn.lemwoodtools.ui.tools

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

/**
 * 导入导出管理器
 */
@Composable
fun ImportExportManager(
    fileName: String,
    content: String,
    onImport: (String, String) -> Unit,
    onExport: (Uri) -> Unit,
    onShare: () -> Unit
) {
    // 使用参数避免警告
    val shareCallback = onShare
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    
    // 导入文件启动器
    val importLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            uri?.let {
                coroutineScope.launch {
                    val result = FileManager.importFile(context, it)
                    result?.let { (fileName, content) ->
                        onImport(fileName, content)
                    }
                }
            }
        }
    )
    
    // 导出文件启动器
    val exportLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("text/markdown"),
        onResult = { uri ->
            uri?.let {
                onExport(it)
            }
        }
    )
    
    // 导入文件
    fun importFile() {
        importLauncher.launch(arrayOf("text/*", "*/*"))
    }
    
    // 导出文件
    fun exportFile() {
        exportLauncher.launch(fileName)
    }
    
    // 分享文件
    fun shareFile() {
        FileManager.shareFile(context, fileName, content)
    }
    
    // 返回导入导出功能
    ImportExportActions(
        onImport = ::importFile,
        onExport = ::exportFile,
        onShare = { shareCallback() }
    )
}

/**
 * 导入导出操作按钮组
 */
@Composable
fun ImportExportActions(
    onImport: () -> Unit,
    onExport: () -> Unit,
    onShare: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        // 导入按钮
        TextButton(
            onClick = onImport,
            modifier = Modifier.weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.FileOpen,
                    contentDescription = "导入",
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("导入")
            }
        }
        
        Spacer(modifier = Modifier.width(8.dp))
        
        // 导出按钮
        TextButton(
            onClick = onExport,
            modifier = Modifier.weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = "导出",
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("导出")
            }
        }
        
        Spacer(modifier = Modifier.width(8.dp))
        
        // 分享按钮
        TextButton(
            onClick = onShare,
            modifier = Modifier.weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "分享",
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("分享")
            }
        }
    }
}