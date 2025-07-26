package cn.lemwood.ui.foundation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import cn.lemwood.ui.foundation.theme.FoundationTheme

/**
 * Foundation对话框
 */
@Composable
fun FoundationDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    properties: DialogProperties = DialogProperties(),
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        Box(
            modifier = modifier
                .clip(FoundationTheme.shapes.dialog)
                .background(FoundationTheme.colors.surface)
                .padding(24.dp)
        ) {
            CompositionLocalProvider(
                androidx.compose.material3.LocalContentColor provides FoundationTheme.colors.onSurface
            ) {
                content()
            }
        }
    }
}

/**
 * Foundation警告对话框
 */
@Composable
fun FoundationAlertDialog(
    onDismissRequest: () -> Unit,
    title: @Composable (() -> Unit)? = null,
    text: @Composable (() -> Unit)? = null,
    confirmButton: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    dismissButton: @Composable (() -> Unit)? = null,
    shape: Shape = FoundationTheme.shapes.dialog,
    backgroundColor: Color = FoundationTheme.colors.surface,
    contentColor: Color = FoundationTheme.colors.onSurface,
    properties: DialogProperties = DialogProperties()
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        Box(
            modifier = modifier
                .clip(shape)
                .background(backgroundColor)
                .padding(24.dp)
        ) {
            Column {
                // 标题
                if (title != null) {
                    CompositionLocalProvider(
                        androidx.compose.material3.LocalContentColor provides contentColor
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            title()
                        }
                    }
                    
                    if (text != null) {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
                
                // 内容文本
                if (text != null) {
                    CompositionLocalProvider(
                        androidx.compose.material3.LocalContentColor provides contentColor.copy(alpha = 0.87f)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            text()
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                }
                
                // 按钮区域
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (dismissButton != null) {
                        dismissButton()
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    confirmButton()
                }
            }
        }
    }
}

/**
 * 简化的警告对话框，接受字符串参数
 */
@Composable
fun FoundationAlertDialog(
    onDismissRequest: () -> Unit,
    title: String? = null,
    text: String? = null,
    confirmButtonText: String,
    onConfirmClick: () -> Unit,
    modifier: Modifier = Modifier,
    dismissButtonText: String? = null,
    onDismissClick: (() -> Unit)? = null,
    shape: Shape = FoundationTheme.shapes.dialog,
    backgroundColor: Color = FoundationTheme.colors.surface,
    contentColor: Color = FoundationTheme.colors.onSurface,
    properties: DialogProperties = DialogProperties()
) {
    FoundationAlertDialog(
        onDismissRequest = onDismissRequest,
        title = if (title != null) {
            {
                FoundationText(
                    text = title,
                    style = FoundationTheme.typography.titleLarge,
                    fontWeight = FontWeight.Medium,
                    color = contentColor
                )
            }
        } else null,
        text = if (text != null) {
            {
                FoundationText(
                    text = text,
                    style = FoundationTheme.typography.bodyMedium,
                    color = contentColor.copy(alpha = 0.87f)
                )
            }
        } else null,
        confirmButton = {
            FoundationButton(
                onClick = onConfirmClick
            ) {
                FoundationText(
                    text = confirmButtonText,
                    color = FoundationTheme.colors.onPrimary
                )
            }
        },
        modifier = modifier,
        dismissButton = if (dismissButtonText != null && onDismissClick != null) {
            {
                FoundationButton(
                    onClick = onDismissClick,
                    colors = ButtonDefaults.textButtonColors()
                ) {
                    FoundationText(
                        text = dismissButtonText,
                        color = FoundationTheme.colors.primary
                    )
                }
            }
        } else null,
        shape = shape,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        properties = properties
    )
}

/**
 * Foundation底部弹出对话框
 */
@Composable
fun FoundationBottomSheetDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = FoundationTheme.shapes.bottomSheet,
    backgroundColor: Color = FoundationTheme.colors.surface,
    contentColor: Color = FoundationTheme.colors.onSurface,
    properties: DialogProperties = DialogProperties(),
    content: @Composable ColumnScope.() -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .clip(shape)
                .background(backgroundColor)
                .padding(16.dp)
        ) {
            // 拖拽指示器
            Box(
                modifier = Modifier
                    .width(32.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(contentColor.copy(alpha = 0.4f))
                    .align(Alignment.CenterHorizontally)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            CompositionLocalProvider(
                androidx.compose.material3.LocalContentColor provides contentColor
            ) {
                content()
            }
        }
    }
}

/**
 * 按钮默认样式
 */
private object ButtonDefaults {
    @Composable
    fun textButtonColors() = object {
        // 这里可以定义文本按钮的颜色
    }
}

/**
 * Foundation确认对话框
 */
@Composable
fun FoundationConfirmDialog(
    onDismissRequest: () -> Unit,
    title: String,
    message: String,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
    confirmText: String = "确认",
    cancelText: String = "取消",
    isDestructive: Boolean = false
) {
    FoundationAlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            FoundationText(
                text = title,
                style = FoundationTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            FoundationText(
                text = message,
                style = FoundationTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            FoundationButton(
                onClick = {
                    onConfirm()
                    onDismissRequest()
                }
            ) {
                FoundationText(
                    text = confirmText,
                    color = if (isDestructive) {
                        Color.White
                    } else {
                        FoundationTheme.colors.onPrimary
                    }
                )
            }
        },
        dismissButton = {
            FoundationButton(
                onClick = onDismissRequest,
                colors = object {} // 文本按钮样式
            ) {
                FoundationText(
                    text = cancelText,
                    color = FoundationTheme.colors.primary
                )
            }
        },
        modifier = modifier
    )
}