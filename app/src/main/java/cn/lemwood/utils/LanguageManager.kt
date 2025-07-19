package cn.lemwood.utils

import android.content.Context
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

/**
 * 语言管理器
 * 负责应用的语言切换和本地化管理
 */
object LanguageManager {
    
    /**
     * 支持的语言枚举
     */
    enum class Language(val code: String, val displayName: String) {
        CHINESE("zh", "中文"),
        ENGLISH("en", "English");
        
        companion object {
            fun fromCode(code: String): Language {
                return values().find { it.code == code } ?: CHINESE
            }
        }
    }
    
    private const val PREF_LANGUAGE = "app_language"
    private const val PREF_NAME = "language_settings"
    
    private val _currentLanguage = MutableStateFlow(Language.CHINESE)
    val currentLanguage: StateFlow<Language> = _currentLanguage.asStateFlow()
    
    /**
     * 初始化语言设置
     * @param context 应用上下文
     */
    fun initialize(context: Context) {
        val savedLanguage = getSavedLanguage(context)
        _currentLanguage.value = savedLanguage
        applyLanguage(context, savedLanguage)
    }
    
    /**
     * 切换语言
     * @param context 应用上下文
     * @param language 目标语言
     */
    fun setLanguage(context: Context, language: Language) {
        if (_currentLanguage.value != language) {
            _currentLanguage.value = language
            saveLanguage(context, language)
            applyLanguage(context, language)
        }
    }
    
    /**
     * 获取当前语言
     */
    fun getCurrentLanguage(): Language = _currentLanguage.value
    
    /**
     * 应用语言设置到Context
     */
    private fun applyLanguage(context: Context, language: Language) {
        val locale = Locale(language.code)
        Locale.setDefault(locale)
        
        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(locale)
        
        context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
    }
    
    /**
     * 保存语言设置
     */
    private fun saveLanguage(context: Context, language: Language) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(PREF_LANGUAGE, language.code).apply()
    }
    
    /**
     * 获取保存的语言设置
     */
    private fun getSavedLanguage(context: Context): Language {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val languageCode = prefs.getString(PREF_LANGUAGE, Language.CHINESE.code) ?: Language.CHINESE.code
        return Language.fromCode(languageCode)
    }
    
    /**
     * 创建带有指定语言的Context
     */
    fun createLocalizedContext(context: Context, language: Language): Context {
        val locale = Locale(language.code)
        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(locale)
        return context.createConfigurationContext(configuration)
    }
    
    /**
     * 获取所有支持的语言
     */
    fun getSupportedLanguages(): List<Language> = Language.values().toList()
}

/**
 * Compose中获取当前语言的扩展函数
 */
@Composable
fun rememberCurrentLanguage(): LanguageManager.Language {
    val currentLanguage by LanguageManager.currentLanguage.collectAsState()
    return currentLanguage
}

/**
 * 获取本地化字符串的扩展函数
 */
fun Context.getLocalizedString(resId: Int, language: LanguageManager.Language): String {
    val localizedContext = LanguageManager.createLocalizedContext(this, language)
    return localizedContext.getString(resId)
}