package cn.lemwood.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

data class ToolItem(
    val id: String,
    val title: String,
    val description: String,
    val icon: ImageVector,
    val route: String,
    val category: String = "通用",
    val keywords: List<String> = emptyList()
)

/**
 * 工具仓库实现类
 * 提供应用中所有工具的数据访问功能
 */
object ToolsRepository : IToolsRepository {
    private val allTools = listOf(
        ToolItem(
            id = "calculator",
            title = "计算器",
            description = "科学计算器和基础计算",
            icon = Icons.Default.Calculate,
            route = "calculator",
            category = "数学",
            keywords = listOf("计算", "数学", "加减乘除", "科学计算")
        ),
        ToolItem(
            id = "converter",
            title = "单位转换",
            description = "长度、重量、温度等单位转换",
            icon = Icons.Default.SwapHoriz,
            route = "converter",
            category = "转换",
            keywords = listOf("转换", "单位", "长度", "重量", "温度", "面积", "体积")
        ),
        ToolItem(
            id = "qrcode",
            title = "二维码",
            description = "二维码生成和扫描",
            icon = Icons.Default.QrCode,
            route = "qrcode",
            category = "工具",
            keywords = listOf("二维码", "扫描", "生成", "条码", "QR")
        ),
        ToolItem(
            id = "text_tools",
            title = "文本工具",
            description = "文本处理和格式化",
            icon = Icons.Default.TextFields,
            route = "text_tools",
            category = "文本",
            keywords = listOf("文本", "格式化", "编码", "解码", "大小写", "字符统计")
        ),
        ToolItem(
            id = "color_picker",
            title = "颜色选择器",
            description = "颜色选择和转换工具",
            icon = Icons.Default.Palette,
            route = "color_picker",
            category = "设计",
            keywords = listOf("颜色", "色彩", "RGB", "HEX", "HSV", "调色板")
        ),
        ToolItem(
            id = "timer",
            title = "计时器",
            description = "倒计时和秒表功能",
            icon = Icons.Default.Timer,
            route = "timer",
            category = "时间",
            keywords = listOf("计时", "倒计时", "秒表", "提醒", "闹钟")
        ),
        ToolItem(
            id = "weather",
            title = "天气查询",
            description = "实时天气和天气预报",
            icon = Icons.Default.Cloud,
            route = "weather",
            category = "生活",
            keywords = listOf("天气", "预报", "温度", "湿度", "风速", "气象")
        ),
        ToolItem(
            id = "notes",
            title = "快速笔记",
            description = "简单的笔记记录工具",
            icon = Icons.Default.Note,
            route = "notes",
            category = "效率",
            keywords = listOf("笔记", "记录", "备忘", "文档", "记事")
        )
    )
    
    override fun getAllTools(): List<ToolItem> = allTools
    
    override fun searchTools(query: String): List<ToolItem> {
        if (query.isBlank()) return allTools
        
        val lowerQuery = query.lowercase().trim()
        return allTools.filter { tool ->
            tool.title.lowercase().contains(lowerQuery) ||
            tool.description.lowercase().contains(lowerQuery) ||
            tool.category.lowercase().contains(lowerQuery) ||
            tool.keywords.any { it.lowercase().contains(lowerQuery) }
        }
    }
    
    override fun getToolsByCategory(): Map<String, List<ToolItem>> {
        return allTools.groupBy { it.category }
    }
    
    override fun getToolById(id: String): ToolItem? {
        return allTools.find { it.id == id }
    }
    
    override fun getCategories(): List<String> {
        return allTools.map { it.category }.distinct().sorted()
    }
}