package cn.lemwood.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/**
 * 扩展函数集合
 * 提供常用的扩展功能，提高代码复用性
 */

// Context扩展
/**
 * 显示短时间Toast
 */
fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

/**
 * 显示长时间Toast
 */
fun Context.showLongToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

// String扩展
/**
 * 检查字符串是否为有效的邮箱地址
 */
fun String.isValidEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

/**
 * 检查字符串是否为有效的手机号码
 */
fun String.isValidPhoneNumber(): Boolean {
    return android.util.Patterns.PHONE.matcher(this).matches()
}

/**
 * 将字符串转换为首字母大写
 */
fun String.capitalize(): String {
    return if (isEmpty()) this else this[0].uppercaseChar() + substring(1).lowercase()
}

/**
 * 安全地转换字符串为整数
 */
fun String.toIntOrDefault(default: Int = 0): Int {
    return try {
        this.toInt()
    } catch (e: NumberFormatException) {
        default
    }
}

/**
 * 安全地转换字符串为浮点数
 */
fun String.toFloatOrDefault(default: Float = 0f): Float {
    return try {
        this.toFloat()
    } catch (e: NumberFormatException) {
        default
    }
}

/**
 * 安全地转换字符串为双精度浮点数
 */
fun String.toDoubleOrDefault(default: Double = 0.0): Double {
    return try {
        this.toDouble()
    } catch (e: NumberFormatException) {
        default
    }
}

/**
 * 移除字符串中的所有空白字符
 */
fun String.removeAllWhitespace(): String {
    return this.replace("\\s".toRegex(), "")
}

/**
 * 检查字符串是否只包含数字
 */
fun String.isNumeric(): Boolean {
    return this.matches("-?\\d+(\\.\\d+)?".toRegex())
}

// List扩展
/**
 * 安全地获取列表中的元素
 */
fun <T> List<T>.safeGet(index: Int): T? {
    return if (index >= 0 && index < size) this[index] else null
}

/**
 * 检查列表是否不为空且不为null
 */
fun <T> List<T>?.isNotNullOrEmpty(): Boolean {
    return this != null && this.isNotEmpty()
}

// Compose扩展
/**
 * 在Compose中显示Toast的便捷方法
 */
@Composable
fun ShowToast(message: String) {
    val context = LocalContext.current
    context.showToast(message)
}

/**
 * 在Compose中显示长Toast的便捷方法
 */
@Composable
fun ShowLongToast(message: String) {
    val context = LocalContext.current
    context.showLongToast(message)
}

// 数字扩展
/**
 * 将数字格式化为带千分位分隔符的字符串
 */
fun Number.formatWithCommas(): String {
    return String.format("%,d", this.toLong())
}

/**
 * 将字节数转换为可读的文件大小格式
 */
fun Long.formatFileSize(): String {
    val units = arrayOf("B", "KB", "MB", "GB", "TB")
    var size = this.toDouble()
    var unitIndex = 0
    
    while (size >= 1024 && unitIndex < units.size - 1) {
        size /= 1024
        unitIndex++
    }
    
    return String.format("%.1f %s", size, units[unitIndex])
}

/**
 * 将毫秒转换为可读的时间格式
 */
fun Long.formatDuration(): String {
    val seconds = this / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    
    return when {
        hours > 0 -> String.format("%d:%02d:%02d", hours, minutes % 60, seconds % 60)
        minutes > 0 -> String.format("%d:%02d", minutes, seconds % 60)
        else -> String.format("0:%02d", seconds)
    }
}