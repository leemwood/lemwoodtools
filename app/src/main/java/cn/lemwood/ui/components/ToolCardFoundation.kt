package cn.lemwood.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cn.lemwood.data.ToolItem
import cn.lemwood.ui.foundation.components.FoundationCard
import cn.lemwood.ui.foundation.components.FoundationText
import cn.lemwood.ui.foundation.theme.FoundationTheme
import cn.lemwood.utils.CategoryHelper
import cn.lemwood.utils.HapticFeedbackHelper

@Composable
fun ToolCardFoundation(
    tool: ToolItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    
    // 获取本地化分类名称
    val localizedCategoryName = CategoryHelper.getLocalizedCategoryName(tool.category)
    
    // 缓存振动支持检查，避免重复系统调用
    val isVibrationSupported = remember {
        HapticFeedbackHelper.isVibrationSupported(context)
    }
    
    FoundationCard(
        onClick = {
            if (isVibrationSupported) {
                HapticFeedbackHelper.buttonClickVibration(context)
            }
            onClick()
        },
        modifier = modifier.fillMaxWidth(),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = tool.icon,
                contentDescription = tool.title,
                modifier = Modifier.size(24.dp),
                tint = FoundationTheme.colors.primary
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                FoundationText(
                    text = tool.title,
                    style = FoundationTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    color = FoundationTheme.colors.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                FoundationText(
                    text = tool.description,
                    style = FoundationTheme.typography.bodySmall,
                    color = FoundationTheme.colors.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                if (localizedCategoryName.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    FoundationText(
                        text = localizedCategoryName,
                        style = FoundationTheme.typography.labelSmall,
                        color = FoundationTheme.colors.primary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}