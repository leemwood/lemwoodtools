package cn.lemwood.ui.foundation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cn.lemwood.ui.foundation.theme.FoundationTheme

/**
 * Foundation卡片组件
 * 轻量级的卡片实现，替代Material3的Card
 */
@Composable
fun FoundationCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    shape: Shape = FoundationTheme.shapes.card,
    backgroundColor: Color = FoundationTheme.colors.surface,
    borderColor: Color = FoundationTheme.colors.outline.copy(alpha = 0.12f),
    borderWidth: Dp = 1.dp,
    elevation: Dp = 2.dp,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable ColumnScope.() -> Unit
) {
    val clickableModifier = if (onClick != null) {
        Modifier.clickable(
            interactionSource = interactionSource,
            indication = rememberRipple(),
            onClick = onClick
        )
    } else {
        Modifier
    }
    
    Column(
        modifier = modifier
            .shadow(elevation = elevation, shape = shape)
            .clip(shape)
            .background(backgroundColor)
            .border(width = borderWidth, color = borderColor, shape = shape)
            .then(clickableModifier)
            .padding(contentPadding),
        content = content
    )
}