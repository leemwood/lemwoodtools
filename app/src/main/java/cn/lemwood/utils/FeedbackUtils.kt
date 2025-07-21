package cn.lemwood.utils

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
    
    /**
     * 发送邮件反馈
     */
    fun sendEmailFeedback(context: Context) {
        try {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf("feedback@lemwood.cn"))
                putExtra(Intent.EXTRA_SUBJECT, "Lemwood Tools 意见反馈")
                putExtra(Intent.EXTRA_TEXT, buildFeedbackTemplate(context))
            }
            
            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
            } else {
                // 如果没有邮件应用，显示提示
                Toast.makeText(
                    context,
                    context.getString(R.string.no_email_app),
                    Toast.LENGTH_SHORT
                ).show()
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
     * 构建反馈邮件模板
     */
    private fun buildFeedbackTemplate(context: Context): String {
        return """
            请在此处描述您的问题或建议：
            
            
            
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
}