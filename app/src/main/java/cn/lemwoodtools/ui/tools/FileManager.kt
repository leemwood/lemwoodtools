package cn.lemwoodtools.ui.tools

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.DocumentsContract
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * 文件管理器 - 处理文件的导入、导出、分享等操作
 */
object FileManager {
    
    /**
     * 获取工作区目录
     */
    fun getWorkspaceDir(context: Context): File {
        val workspaceDir = File(context.filesDir, "markdown_workspace")
        if (!workspaceDir.exists()) {
            workspaceDir.mkdirs()
        }
        return workspaceDir
    }
    
    /**
     * 获取工作区中的所有Markdown文件
     */
    fun getMarkdownFiles(context: Context): List<FileInfo> {
        val workspaceDir = getWorkspaceDir(context)
        return workspaceDir.listFiles { file ->
            file.isFile && (file.name.endsWith(".md") || file.name.endsWith(".markdown"))
        }?.map { file ->
            FileInfo(
                name = file.name,
                path = file.absolutePath,
                size = file.length(),
                lastModified = Date(file.lastModified()),
                isDirectory = false
            )
        }?.sortedByDescending { it.lastModified } ?: emptyList()
    }
    
    /**
     * 保存Markdown文件
     */
    fun saveMarkdownFile(context: Context, fileName: String, content: String): Boolean {
        return try {
            val workspaceDir = getWorkspaceDir(context)
            val file = File(workspaceDir, fileName)
            file.writeText(content, Charsets.UTF_8)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
    
    /**
     * 加载Markdown文件
     */
    fun loadMarkdownFile(context: Context, fileName: String): String {
        val workspaceDir = getWorkspaceDir(context)
        val file = File(workspaceDir, fileName)
        return if (file.exists()) {
            try {
                file.readText(Charsets.UTF_8)
            } catch (e: Exception) {
                e.printStackTrace()
                ""
            }
        } else {
            ""
        }
    }
    
    /**
     * 删除文件
     */
    fun deleteFile(context: Context, fileName: String): Boolean {
        val workspaceDir = getWorkspaceDir(context)
        val file = File(workspaceDir, fileName)
        return try {
            if (file.exists()) {
                file.delete()
            } else {
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
    
    /**
     * 重命名文件
     */
    fun renameFile(context: Context, oldName: String, newName: String): Boolean {
        val workspaceDir = getWorkspaceDir(context)
        val oldFile = File(workspaceDir, oldName)
        val newFile = File(workspaceDir, newName)
        
        return try {
            if (oldFile.exists() && !newFile.exists()) {
                oldFile.renameTo(newFile)
            } else {
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
    
    /**
     * 创建新文件
     */
    fun createNewFile(context: Context, fileName: String, content: String = ""): Boolean {
        return saveMarkdownFile(context, fileName, content)
    }
    
    /**
     * 导出文件到外部存储
     */
    fun exportFile(context: Context, fileName: String, content: String, uri: Uri): Boolean {
        return try {
            context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                // 确保内容以UTF-8编码写入，并添加适当的换行符
                val formattedContent = content.trim() + "\n"
                outputStream.write(formattedContent.toByteArray(Charsets.UTF_8))
                outputStream.flush()
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
    
    /**
     * 从URI导入文件
     */
    fun importFile(context: Context, uri: Uri): Pair<String, String>? {
        return try {
            val content = context.contentResolver.openInputStream(uri)?.use { inputStream ->
                inputStream.bufferedReader(Charsets.UTF_8).use { it.readText() }
            } ?: return null
            
            // 获取原始文件名
            val fileName = getFileNameFromUri(context, uri) ?: "imported_${System.currentTimeMillis()}.md"
            
            // 确保文件名唯一
            val uniqueFileName = generateUniqueFileName(context, fileName)
            
            Pair(uniqueFileName, content)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    
    /**
     * 分享文件（打包成文件分享）
     */
    fun shareFile(context: Context, fileName: String, content: String) {
        try {
            // 创建临时文件
            val tempFile = File.createTempFile("share_", ".md", context.cacheDir)
            tempFile.writeText(content, Charsets.UTF_8)
            
            // 获取文件的URI
            val fileUri = androidx.core.content.FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider",
                tempFile
            )
            
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/markdown"
                putExtra(Intent.EXTRA_STREAM, fileUri)
                putExtra(Intent.EXTRA_SUBJECT, fileName)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            
            val chooser = Intent.createChooser(intent, "分享 $fileName")
            context.startActivity(chooser)
            
            // 在分享完成后删除临时文件（延迟执行）
            android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                try {
                    if (tempFile.exists()) {
                        tempFile.delete()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }, 30000) // 30秒后删除临时文件
            
        } catch (e: Exception) {
            e.printStackTrace()
            // 如果文件分享失败，回退到文本分享
            fallbackShareText(context, fileName, content)
        }
    }
    
    /**
     * 回退到文本分享（当文件分享失败时使用）
     */
    private fun fallbackShareText(context: Context, fileName: String, content: String) {
        try {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, content)
                putExtra(Intent.EXTRA_SUBJECT, fileName)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            
            val chooser = Intent.createChooser(intent, "分享 $fileName")
            context.startActivity(chooser)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    /**
     * 从URI获取文件名
     */
    private fun getFileNameFromUri(context: Context, uri: Uri): String? {
        var fileName: String? = null
        
        if (DocumentsContract.isDocumentUri(context, uri)) {
            context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val displayNameIndex = cursor.getColumnIndex(DocumentsContract.Document.COLUMN_DISPLAY_NAME)
                    if (displayNameIndex != -1) {
                        fileName = cursor.getString(displayNameIndex)
                    }
                }
            }
        }
        
        return fileName
    }
    
    /**
     * 生成唯一文件名
     */
    private fun generateUniqueFileName(context: Context, originalName: String): String {
        val workspaceDir = getWorkspaceDir(context)
        var fileName = originalName
        var counter = 1
        
        while (File(workspaceDir, fileName).exists()) {
            val nameWithoutExt = originalName.substringBeforeLast(".")
            val ext = originalName.substringAfterLast(".", "")
            fileName = if (ext.isNotEmpty()) {
                "${nameWithoutExt}_$counter.$ext"
            } else {
                "${originalName}_$counter"
            }
            counter++
        }
        
        return fileName
    }
    
    /**
     * 获取文件大小的人性化显示
     */
    fun formatFileSize(size: Long): String {
        val kb = 1024
        val mb = kb * 1024
        val gb = mb * 1024
        
        return when {
            size < kb -> "${size}B"
            size < mb -> "${String.format("%.1f", size.toDouble() / kb)}KB"
            size < gb -> "${String.format("%.1f", size.toDouble() / mb)}MB"
            else -> "${String.format("%.1f", size.toDouble() / gb)}GB"
        }
    }
    
    /**
     * 格式化日期
     */
    fun formatDate(date: Date): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        return sdf.format(date)
    }
}

/**
 * 文件信息数据类
 */
data class FileInfo(
    val name: String,
    val path: String,
    val size: Long,
    val lastModified: Date,
    val isDirectory: Boolean
)