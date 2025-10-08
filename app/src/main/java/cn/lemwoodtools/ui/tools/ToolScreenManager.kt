package cn.lemwoodtools.ui.tools

import androidx.compose.runtime.*

/**
 * 工具屏幕管理器
 */
class ToolScreenManager {
    var currentTool: Tool? by mutableStateOf(null)
    
    fun openTool(tool: Tool) {
        currentTool = tool
    }
    
    fun closeTool() {
        currentTool = null
    }
    
    fun isToolOpen(): Boolean {
        return currentTool != null
    }
}

/**
 * 提供工具屏幕管理器的Composable函数
 */
@Composable
fun rememberToolScreenManager(): ToolScreenManager {
    return remember { ToolScreenManager() }
}

/**
 * 工具屏幕包装器
 */
@Composable
fun ToolScreenWrapper(
    tool: Tool,
    toolScreenManager: ToolScreenManager,
    content: @Composable (onBackClick: () -> Unit) -> Unit
) {
    content { toolScreenManager.closeTool() }
}