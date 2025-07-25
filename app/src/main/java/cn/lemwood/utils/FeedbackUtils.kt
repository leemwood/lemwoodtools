package cn.lemwood.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import cn.lemwood.R

/**
 * 反馈工具类
 * 负责处理用户反馈功能
 */
object FeedbackUtils {
    
    // QQ号
    private const val QQ_NUMBER = "3436464181"
    // GitHub用户名
    private const val GITHUB_USERNAME = "ning-g-mo"
    // GitHub仓库名
    private const val GITHUB_REPO = "LemwoodTools"
    
    /**
     * 获取设备信息用于反馈
     */
    fun getDeviceInfo(): String {
        return """
            ===== 系统信息 =====
            应用版本: 1.0.0
            Android版本: ${android.os.Build.VERSION.RELEASE}
            设备型号: ${android.os.Build.MODEL}
            设备厂商: ${android.os.Build.MANUFACTURER}
            ==================
        """.trimIndent()
    }
    
    /**
     * 打开应用商店评分
     */
    fun openAppStore(context: Context) {
        try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("market://details?id=${context.packageName}")
            }
            
            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
            } else {
                // 如果没有应用商店，打开网页版
                val webIntent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("https://play.google.com/store/apps/details?id=${context.packageName}")
                }
                context.startActivity(webIntent)
            }
        } catch (e: Exception) {
            Toast.makeText(
                context,
                context.getString(R.string.feedback_error),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    
    /**
     * 联系QQ反馈
     */
    fun contactQQ(context: Context) {
        try {
            // 尝试打开QQ聊天
            val qqIntent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin=$QQ_NUMBER")
            }
            
            if (qqIntent.resolveActivity(context.packageManager) != null) {
                context.startActivity(qqIntent)
            } else {
                // 如果没有QQ应用，复制QQ号到剪贴板
                copyQQToClipboard(context)
            }
        } catch (e: Exception) {
            // 出错时复制QQ号到剪贴板
            copyQQToClipboard(context)
        }
    }
    
    /**
     * 复制QQ号到剪贴板
     */
    private fun copyQQToClipboard(context: Context) {
        try {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("QQ号", QQ_NUMBER)
            clipboard.setPrimaryClip(clip)
            
            Toast.makeText(
                context,
                "QQ号 $QQ_NUMBER 已复制到剪贴板",
                Toast.LENGTH_LONG
            ).show()
        } catch (e: Exception) {
            Toast.makeText(
                context,
                "QQ号: $QQ_NUMBER",
                Toast.LENGTH_LONG
            ).show()
        }
    }
    
    /**
     * 打开GitHub仓库
     */
    fun openGitHubRepo(context: Context) {
        try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://github.com/$GITHUB_USERNAME/$GITHUB_REPO")
            }
            
            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
            } else {
                // 如果无法打开浏览器，复制链接到剪贴板
                copyGitHubLinkToClipboard(context)
            }
        } catch (e: Exception) {
            copyGitHubLinkToClipboard(context)
        }
    }
    
    /**
     * 打开GitHub Issues页面
     */
    fun openGitHubIssues(context: Context) {
        try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://github.com/$GITHUB_USERNAME/$GITHUB_REPO/issues")
            }
            
            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
            } else {
                copyGitHubLinkToClipboard(context)
            }
        } catch (e: Exception) {
            copyGitHubLinkToClipboard(context)
        }
    }
    
    /**
     * 复制GitHub链接到剪贴板
     */
    private fun copyGitHubLinkToClipboard(context: Context) {
        try {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val githubUrl = "https://github.com/$GITHUB_USERNAME/$GITHUB_REPO"
            val clip = ClipData.newPlainText("GitHub仓库", githubUrl)
            clipboard.setPrimaryClip(clip)
            
            Toast.makeText(
                context,
                "GitHub仓库链接已复制到剪贴板",
                Toast.LENGTH_LONG
            ).show()
        } catch (e: Exception) {
            Toast.makeText(
                context,
                "GitHub: $GITHUB_USERNAME/$GITHUB_REPO",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}