package cn.lemwood.ui.foundation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cn.lemwood.ui.foundation.theme.FoundationTheme

/**
 * Foundation底部导航栏
 */
@Composable
fun FoundationBottomNavigation(
    modifier: Modifier = Modifier,
    backgroundColor: Color = FoundationTheme.colors.surface,
    contentColor: Color = FoundationTheme.colors.onSurface,
    elevation: dp = 8.dp,
    content: @Composable RowScope.() -> Unit
) {
    Surface(
        modifier = modifier,
        color = backgroundColor,
        contentColor = contentColor,
        elevation = elevation
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .selectableGroup(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            content = content
        )
    }
}

/**
 * Foundation底部导航栏项目
 */
@Composable
fun RowScope.FoundationBottomNavigationItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true,
    selectedContentColor: Color = FoundationTheme.colors.primary,
    unselectedContentColor: Color = FoundationTheme.colors.onSurfaceVariant,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val animatedColor by animateColorAsState(
        targetValue = if (selected) selectedContentColor else unselectedContentColor,
        animationSpec = tween(durationMillis = 150),
        label = "color"
    )
    
    val animatedAlpha by animateFloatAsState(
        targetValue = if (selected || alwaysShowLabel) 1f else 0f,
        animationSpec = tween(durationMillis = 150),
        label = "alpha"
    )
    
    Box(
        modifier = modifier
            .weight(1f)
            .clickable(
                onClick = onClick,
                enabled = enabled,
                role = Role.Tab,
                interactionSource = interactionSource,
                indication = rememberRipple(
                    bounded = false,
                    radius = 32.dp,
                    color = selectedContentColor
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier.size(24.dp),
                contentAlignment = Alignment.Center
            ) {
                CompositionLocalProvider(
                    androidx.compose.material3.LocalContentColor provides animatedColor
                ) {
                    icon()
                }
            }
            
            if (label != null) {
                Spacer(modifier = Modifier.height(4.dp))
                
                Box(
                    modifier = Modifier.alpha(animatedAlpha)
                ) {
                    CompositionLocalProvider(
                        androidx.compose.material3.LocalContentColor provides animatedColor
                    ) {
                        FoundationText(
                            text = "",
                            style = FoundationTheme.typography.labelSmall,
                            fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal,
                            color = animatedColor
                        )
                        label()
                    }
                }
            }
        }
    }
}

/**
 * 简化的底部导航栏项目数据类
 */
data class BottomNavigationItem(
    val icon: ImageVector,
    val selectedIcon: ImageVector? = null,
    val label: String,
    val route: String
)

/**
 * 预构建的底部导航栏
 */
@Composable
fun FoundationBottomNavigationBar(
    items: List<BottomNavigationItem>,
    selectedRoute: String,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = FoundationTheme.colors.surface,
    selectedContentColor: Color = FoundationTheme.colors.primary,
    unselectedContentColor: Color = FoundationTheme.colors.onSurfaceVariant
) {
    FoundationBottomNavigation(
        modifier = modifier,
        backgroundColor = backgroundColor
    ) {
        items.forEach { item ->
            val selected = selectedRoute == item.route
            
            FoundationBottomNavigationItem(
                selected = selected,
                onClick = { onItemClick(item.route) },
                selectedContentColor = selectedContentColor,
                unselectedContentColor = unselectedContentColor,
                icon = {
                    Icon(
                        imageVector = if (selected && item.selectedIcon != null) {
                            item.selectedIcon
                        } else {
                            item.icon
                        },
                        contentDescription = item.label
                    )
                },
                label = {
                    FoundationText(
                        text = item.label,
                        style = FoundationTheme.typography.labelSmall,
                        fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal
                    )
                }
            )
        }
    }
}

/**
 * Surface组件 - 用于底部导航栏的背景
 */
@Composable
private fun Surface(
    modifier: Modifier = Modifier,
    color: Color = FoundationTheme.colors.surface,
    contentColor: Color = FoundationTheme.colors.onSurface,
    elevation: dp = 0.dp,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .background(color)
            .padding(vertical = if (elevation > 0.dp) 2.dp else 0.dp)
    ) {
        CompositionLocalProvider(
            androidx.compose.material3.LocalContentColor provides contentColor
        ) {
            content()
        }
    }
}