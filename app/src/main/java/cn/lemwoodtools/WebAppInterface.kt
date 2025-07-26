package cn.lemwoodtools

import android.content.Context
import android.webkit.JavascriptInterface
import android.widget.Toast

class WebAppInterface(private val context: Context) {
    
    @JavascriptInterface
    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
    
    @JavascriptInterface
    fun getAppVersion(): String {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.versionName
        } catch (e: Exception) {
            "1.0.0"
        }
    }
    
    @JavascriptInterface
    fun vibrate(duration: Long) {
        // Implementation for device vibration
    }
    
    @JavascriptInterface
    fun shareText(text: String) {
        // Implementation for sharing functionality
    }
    
    @JavascriptInterface
    fun copyToClipboard(text: String) {
        // Implementation for clipboard functionality
    }
}