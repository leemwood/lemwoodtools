package cn.lemwoodtools.ui.tools

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit

/**
 * 工具数据模型
 */
data class Tool(
    val id: Int,
    val name: String,
    val description: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val screen: @Composable (onBackClick: () -> Unit) -> Unit
)

/**
 * 工具列表
 */
object ToolsManager {
    val tools = listOf(
        Tool(
            id = 1,
            name = "Markdown编辑器",
            description = "编辑和预览Markdown文档",
            icon = Icons.Filled.Edit,
            screen = { onBackClick -> MarkdownEditorScreen(onBackClick = onBackClick) }
        )
        // 可以在这里添加更多工具
    )
}