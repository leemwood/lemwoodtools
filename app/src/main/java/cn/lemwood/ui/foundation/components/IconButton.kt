package cn.lemwood.ui.foundation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cn.lemwood.ui.foundation.theme.FoundationTheme

@Composable
fun FoundationIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit
) {
    val colors = FoundationTheme.colors
    
    Box(
        modifier = modifier
            .size(48.dp)
            .clip(CircleShape)
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(
                    bounded = false,
                    radius = 24.dp
                ),
                enabled = enabled,
                role = Role.Button,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
fun FoundationFilledIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    containerColor: Color = FoundationTheme.colors.primary,
    contentColor: Color = FoundationTheme.colors.onPrimary,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit
) {
    val colors = FoundationTheme.colors
    
    val actualContainerColor = if (enabled) containerColor else colors.onSurface.copy(alpha = 0.12f)
    val actualContentColor = if (enabled) contentColor else colors.onSurface.copy(alpha = 0.38f)
    
    Box(
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(actualContainerColor)
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(
                    bounded = true,
                    radius = 20.dp,
                    color = actualContentColor
                ),
                enabled = enabled,
                role = Role.Button,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        CompositionLocalProvider(
            androidx.compose.material3.LocalContentColor provides actualContentColor
        ) {
            content()
        }
    }
}

@Composable
fun FoundationOutlinedIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    containerColor: Color = Color.Transparent,
    contentColor: Color = FoundationTheme.colors.onSurfaceVariant,
    borderColor: Color = FoundationTheme.colors.outline,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit
) {
    val colors = FoundationTheme.colors
    
    val actualContainerColor = if (enabled) containerColor else Color.Transparent
    val actualContentColor = if (enabled) contentColor else colors.onSurface.copy(alpha = 0.38f)
    val actualBorderColor = if (enabled) borderColor else colors.onSurface.copy(alpha = 0.12f)
    
    Box(
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(actualContainerColor)
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(
                    bounded = true,
                    radius = 20.dp,
                    color = actualContentColor
                ),
                enabled = enabled,
                role = Role.Button,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        CompositionLocalProvider(
            androidx.compose.material3.LocalContentColor provides actualContentColor
        ) {
            content()
        }
    }
}

@Composable
fun FoundationFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: androidx.compose.ui.graphics.Shape = FoundationTheme.shapes.large,
    containerColor: Color = FoundationTheme.colors.primaryContainer,
    contentColor: Color = FoundationTheme.colors.onPrimaryContainer,
    elevation: Dp = 6.dp,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit
) {
    androidx.compose.material3.Surface(
        onClick = onClick,
        modifier = modifier.size(56.dp),
        shape = shape,
        color = containerColor,
        contentColor = contentColor,
        tonalElevation = elevation,
        shadowElevation = elevation,
        interactionSource = interactionSource
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}

@Composable
fun FoundationSmallFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: androidx.compose.ui.graphics.Shape = FoundationTheme.shapes.medium,
    containerColor: Color = FoundationTheme.colors.primaryContainer,
    contentColor: Color = FoundationTheme.colors.onPrimaryContainer,
    elevation: Dp = 6.dp,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit
) {
    androidx.compose.material3.Surface(
        onClick = onClick,
        modifier = modifier.size(40.dp),
        shape = shape,
        color = containerColor,
        contentColor = contentColor,
        tonalElevation = elevation,
        shadowElevation = elevation,
        interactionSource = interactionSource
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}