package cn.lemwood.ui.foundation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cn.lemwood.ui.foundation.theme.FoundationTheme

/**
 * Foundation顶部应用栏
 */
@Composable
fun FoundationTopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = FoundationTheme.colors.surface,
    contentColor: Color = FoundationTheme.colors.onSurface,
    elevation: dp = 4.dp
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
                .height(64.dp)
                .padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 导航图标
            if (navigationIcon != null) {
                Box(
                    modifier = Modifier
                        .size(48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    navigationIcon()
                }
            }
            
            // 标题
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = if (navigationIcon != null) 8.dp else 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                CompositionLocalProvider(
                    androidx.compose.material3.LocalContentColor provides contentColor
                ) {
                    title()
                }
            }
            
            // 操作按钮
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                content = actions
            )
        }
    }
}

/**
 * 简化的顶部应用栏，接受字符串标题
 */
@Composable
fun FoundationTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = FoundationTheme.colors.surface,
    contentColor: Color = FoundationTheme.colors.onSurface,
    elevation: dp = 4.dp
) {
    FoundationTopAppBar(
        title = {
            FoundationText(
                text = title,
                style = FoundationTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium,
                color = contentColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        modifier = modifier,
        navigationIcon = navigationIcon,
        actions = actions,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        elevation = elevation
    )
}

/**
 * 大型顶部应用栏
 */
@Composable
fun FoundationLargeTopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = FoundationTheme.colors.surface,
    contentColor: Color = FoundationTheme.colors.onSurface,
    elevation: dp = 4.dp
) {
    Surface(
        modifier = modifier,
        color = backgroundColor,
        contentColor = contentColor,
        elevation = elevation
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(112.dp)
        ) {
            // 顶部工具栏
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 导航图标
                if (navigationIcon != null) {
                    Box(
                        modifier = Modifier.size(48.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        navigationIcon()
                    }
                }
                
                Spacer(modifier = Modifier.weight(1f))
                
                // 操作按钮
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    content = actions
                )
            }
            
            // 大标题
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = if (navigationIcon != null) 56.dp else 16.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                CompositionLocalProvider(
                    androidx.compose.material3.LocalContentColor provides contentColor
                ) {
                    title()
                }
            }
        }
    }
}

/**
 * 简化的大型顶部应用栏，接受字符串标题
 */
@Composable
fun FoundationLargeTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = FoundationTheme.colors.surface,
    contentColor: Color = FoundationTheme.colors.onSurface,
    elevation: dp = 4.dp
) {
    FoundationLargeTopAppBar(
        title = {
            FoundationText(
                text = title,
                style = FoundationTheme.typography.headlineMedium,
                fontWeight = FontWeight.Normal,
                color = contentColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        modifier = modifier,
        navigationIcon = navigationIcon,
        actions = actions,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        elevation = elevation
    )
}

/**
 * 顶部应用栏操作按钮
 */
@Composable
fun FoundationTopAppBarAction(
    icon: ImageVector,
    contentDescription: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    tint: Color = FoundationTheme.colors.onSurface
) {
    FoundationIconButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = tint
        )
    }
}

/**
 * Surface组件 - 用于顶部应用栏的背景
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
            .padding(vertical = if (elevation > 0.dp) 1.dp else 0.dp)
    ) {
        CompositionLocalProvider(
            androidx.compose.material3.LocalContentColor provides contentColor
        ) {
            content()
        }
    }
}