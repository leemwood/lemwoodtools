package cn.lemwood.utils

import android.util.Log

/**
 * 错误处理工具类
 * 提供统一的错误处理和日志记录功能
 */
object ErrorHandler {
    private const val TAG = "LemwoodTools"
    
    /**
     * 处理一般错误
     * @param error 错误信息
     * @param throwable 异常对象（可选）
     */
    fun handleError(error: String, throwable: Throwable? = null) {
        Log.e(TAG, error, throwable)
        // 这里可以添加错误上报逻辑
    }
    
    /**
     * 处理网络错误
     * @param error 错误信息
     * @param throwable 异常对象（可选）
     */
    fun handleNetworkError(error: String, throwable: Throwable? = null) {
        Log.e(TAG, "Network Error: $error", throwable)
        // 这里可以添加网络错误特定处理逻辑
    }
    
    /**
     * 记录警告信息
     * @param message 警告信息
     */
    fun logWarning(message: String) {
        Log.w(TAG, message)
    }
    
    /**
     * 记录调试信息
     * @param message 调试信息
     */
    fun logDebug(message: String) {
        Log.d(TAG, message)
    }
    
    /**
     * 记录信息
     * @param message 信息内容
     */
    fun logInfo(message: String) {
        Log.i(TAG, message)
    }
    
    /**
     * 安全执行代码块，捕获异常
     * @param action 要执行的代码块
     * @param onError 错误处理回调（可选）
     */
    inline fun <T> safeExecute(
        action: () -> T,
        onError: ((Throwable) -> T)? = null
    ): T? {
        return try {
            action()
        } catch (e: Exception) {
            handleError("Safe execution failed", e)
            onError?.invoke(e)
        }
    }
    
    /**
     * 安全执行挂起函数
     * @param action 要执行的挂起函数
     * @param onError 错误处理回调（可选）
     */
    suspend inline fun <T> safeExecuteSuspend(
        crossinline action: suspend () -> T,
        crossinline onError: (suspend (Throwable) -> T)? = null
    ): T? {
        return try {
            action()
        } catch (e: Exception) {
            handleError("Safe suspend execution failed", e)
            onError?.invoke(e)
        }
    }
}