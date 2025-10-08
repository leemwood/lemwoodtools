package cn.lemwoodtools.ui.tools

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import io.noties.markwon.Markwon
import io.noties.markwon.html.HtmlPlugin
import io.noties.markwon.image.ImagesPlugin
import io.noties.markwon.linkify.LinkifyPlugin

/**
 * Markdown预览组件
 */
@Composable
fun MarkdownPreview(
    markdownText: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val markwon = remember { createMarkwon(context) }
    
    Column(modifier = modifier) {
        AndroidView(
            factory = { ctx ->
                val textView = android.widget.TextView(ctx)
                textView.textSize = 14f
                textView.setTextColor(android.graphics.Color.BLACK)
                textView.setLinkTextColor(android.graphics.Color.BLUE)
                textView
            },
            update = { textView ->
                markwon.setMarkdown(textView, markdownText)
            },
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        )
    }
}

/**
 * 创建Markwon实例
 */
private fun createMarkwon(context: Context): Markwon {
    return Markwon.builder(context)
        .usePlugin(HtmlPlugin.create())
        .usePlugin(ImagesPlugin.create())
        .usePlugin(LinkifyPlugin.create())
        .build()
}

/**
 * 简单的文本预览（备用方案）
 */
@Composable
fun SimpleMarkdownPreview(
    markdownText: String,
    modifier: Modifier = Modifier
) {
    val processedText = remember(markdownText) {
        processMarkdownText(markdownText)
    }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = processedText,
            style = TextStyle(fontSize = 14.sp),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

/**
 * 简单的Markdown文本处理
 */
private fun processMarkdownText(text: String): String {
    return text
        .replace("# ", "") // 移除标题标记
        .replace("## ", "") // 移除二级标题标记
        .replace("### ", "") // 移除三级标题标记
        .replace("**", "") // 移除粗体标记
        .replace("*", "") // 移除斜体标记
        .replace("`", "") // 移除代码标记
        .replace("```", "") // 移除代码块标记
}