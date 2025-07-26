package cn.lemwood.ui.foundation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import cn.lemwood.ui.foundation.theme.FoundationTheme

/**
 * Foundation按钮组件
 * 轻量级的按钮实现，替代Material3的Button
 */
@Composable
fun FoundationButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = FoundationTheme.shapes.button,
    backgroundColor: Color = FoundationTheme.colors.primary,
    contentColor: Color = FoundationTheme.colors.onPrimary,
    disabledBackgroundColor: Color = FoundationTheme.colors.outline.copy(alpha = 0.12f),
    disabledContentColor: Color = FoundationTheme.colors.onSurface.copy(alpha = 0.38f),
    contentPadding: PaddingValues = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit
) {
    val finalBackgroundColor = if (enabled) backgroundColor else disabledBackgroundColor
    val finalContentColor = if (enabled) contentColor else disabledContentColor
    
    CompositionLocalProvider(LocalContentColor provides finalContentColor) {
        Row(
            modifier = modifier
                .clip(shape)
                .background(finalBackgroundColor)
                .clickable(
                    interactionSource = interactionSource,
                    indication = rememberRipple(),
                    enabled = enabled,
                    role = Role.Button,
                    onClick = onClick
                )
                .padding(contentPadding),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            content = content
        )
    }
}