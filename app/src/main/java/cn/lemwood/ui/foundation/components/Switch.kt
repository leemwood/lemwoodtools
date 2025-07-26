package cn.lemwood.ui.foundation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cn.lemwood.ui.foundation.theme.FoundationTheme

@Composable
fun FoundationSwitch(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val colors = FoundationTheme.colors
    
    // 动画进度
    val animationProgress by animateFloatAsState(
        targetValue = if (checked) 1f else 0f,
        animationSpec = tween(durationMillis = 150),
        label = "SwitchAnimation"
    )
    
    // 颜色计算
    val trackColor = when {
        !enabled -> colors.onSurface.copy(alpha = 0.12f)
        checked -> colors.primary
        else -> colors.outline
    }
    
    val thumbColor = when {
        !enabled -> colors.onSurface.copy(alpha = 0.38f)
        checked -> colors.onPrimary
        else -> colors.outline
    }
    
    val density = LocalDensity.current
    val trackWidth = 52.dp
    val trackHeight = 32.dp
    val thumbSize = 20.dp
    val trackPadding = 6.dp
    
    Box(
        modifier = modifier
            .size(width = trackWidth, height = trackHeight)
            .clip(CircleShape)
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(
                    bounded = false,
                    radius = 24.dp
                ),
                enabled = enabled,
                role = Role.Switch,
                onClick = { onCheckedChange?.invoke(!checked) }
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawSwitchTrack(
                trackColor = trackColor,
                trackWidth = trackWidth,
                trackHeight = trackHeight,
                cornerRadius = trackHeight / 2
            )
            
            drawSwitchThumb(
                thumbColor = thumbColor,
                thumbSize = thumbSize,
                trackPadding = trackPadding,
                animationProgress = animationProgress,
                trackWidth = trackWidth,
                trackHeight = trackHeight
            )
        }
    }
}

private fun DrawScope.drawSwitchTrack(
    trackColor: Color,
    trackWidth: Dp,
    trackHeight: Dp,
    cornerRadius: Dp
) {
    val trackWidthPx = trackWidth.toPx()
    val trackHeightPx = trackHeight.toPx()
    val cornerRadiusPx = cornerRadius.toPx()
    
    drawRoundRect(
        color = trackColor,
        topLeft = Offset.Zero,
        size = Size(trackWidthPx, trackHeightPx),
        cornerRadius = CornerRadius(cornerRadiusPx)
    )
}

private fun DrawScope.drawSwitchThumb(
    thumbColor: Color,
    thumbSize: Dp,
    trackPadding: Dp,
    animationProgress: Float,
    trackWidth: Dp,
    trackHeight: Dp
) {
    val thumbSizePx = thumbSize.toPx()
    val trackPaddingPx = trackPadding.toPx()
    val trackWidthPx = trackWidth.toPx()
    val trackHeightPx = trackHeight.toPx()
    
    val thumbRadius = thumbSizePx / 2
    val maxThumbOffset = trackWidthPx - thumbSizePx - trackPaddingPx * 2
    val thumbOffset = trackPaddingPx + (maxThumbOffset * animationProgress)
    
    val thumbCenter = Offset(
        x = thumbOffset + thumbRadius,
        y = trackHeightPx / 2
    )
    
    drawCircle(
        color = thumbColor,
        radius = thumbRadius,
        center = thumbCenter
    )
}

@Composable
fun FoundationCheckbox(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val colors = FoundationTheme.colors
    val shapes = FoundationTheme.shapes
    
    // 动画进度
    val animationProgress by animateFloatAsState(
        targetValue = if (checked) 1f else 0f,
        animationSpec = tween(durationMillis = 150),
        label = "CheckboxAnimation"
    )
    
    // 颜色计算
    val backgroundColor = when {
        !enabled -> colors.onSurface.copy(alpha = 0.12f)
        checked -> colors.primary
        else -> Color.Transparent
    }
    
    val borderColor = when {
        !enabled -> colors.onSurface.copy(alpha = 0.38f)
        checked -> colors.primary
        else -> colors.outline
    }
    
    val checkmarkColor = when {
        !enabled -> colors.onSurface.copy(alpha = 0.38f)
        else -> colors.onPrimary
    }
    
    Box(
        modifier = modifier
            .size(20.dp)
            .clip(shapes.extraSmall)
            .background(backgroundColor)
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(
                    bounded = false,
                    radius = 20.dp
                ),
                enabled = enabled,
                role = Role.Checkbox,
                onClick = { onCheckedChange?.invoke(!checked) }
            ),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val strokeWidth = 2.dp.toPx()
            val checkmarkPath = androidx.compose.ui.graphics.Path()
            
            if (!checked) {
                // 绘制边框
                drawRoundRect(
                    color = borderColor,
                    style = androidx.compose.ui.graphics.drawscope.Stroke(width = strokeWidth),
                    cornerRadius = CornerRadius(2.dp.toPx())
                )
            } else {
                // 绘制选中状态的勾号
                val checkmarkStroke = androidx.compose.ui.graphics.drawscope.Stroke(
                    width = strokeWidth,
                    cap = androidx.compose.ui.graphics.StrokeCap.Round,
                    join = androidx.compose.ui.graphics.StrokeJoin.Round
                )
                
                val checkmarkSize = size.minDimension * 0.7f
                val checkmarkOffset = (size.minDimension - checkmarkSize) / 2
                
                checkmarkPath.moveTo(
                    checkmarkOffset + checkmarkSize * 0.2f,
                    checkmarkOffset + checkmarkSize * 0.5f
                )
                checkmarkPath.lineTo(
                    checkmarkOffset + checkmarkSize * 0.45f,
                    checkmarkOffset + checkmarkSize * 0.7f
                )
                checkmarkPath.lineTo(
                    checkmarkOffset + checkmarkSize * 0.8f,
                    checkmarkOffset + checkmarkSize * 0.3f
                )
                
                drawPath(
                    path = checkmarkPath,
                    color = checkmarkColor.copy(alpha = animationProgress),
                    style = checkmarkStroke
                )
            }
        }
    }
}