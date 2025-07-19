package cn.lemwood.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import cn.lemwood.R

/**
 * 分类助手类
 * 提供分类名称的国际化支持
 */
object CategoryHelper {
    
    /**
     * 获取本地化的分类名称
     */
    @Composable
    fun getLocalizedCategoryName(categoryKey: String): String {
        return when (categoryKey) {
            "category_math" -> stringResource(R.string.category_math)
            "category_conversion" -> stringResource(R.string.category_conversion)
            "category_tools" -> stringResource(R.string.category_tools)
            "category_text" -> stringResource(R.string.category_text)
            "category_design" -> stringResource(R.string.category_design)
            "category_time" -> stringResource(R.string.category_time)
            "category_life" -> stringResource(R.string.category_life)
            "category_productivity" -> stringResource(R.string.category_productivity)
            else -> categoryKey
        }
    }
    
    /**
     * 获取所有分类的键值对映射（键 -> 本地化名称）
     */
    @Composable
    fun getCategoryMap(): Map<String, String> {
        return mapOf(
            "category_math" to stringResource(R.string.category_math),
            "category_conversion" to stringResource(R.string.category_conversion),
            "category_tools" to stringResource(R.string.category_tools),
            "category_text" to stringResource(R.string.category_text),
            "category_design" to stringResource(R.string.category_design),
            "category_time" to stringResource(R.string.category_time),
            "category_life" to stringResource(R.string.category_life),
            "category_productivity" to stringResource(R.string.category_productivity)
        )
    }
}