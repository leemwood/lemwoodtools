package cn.lemwood.utils

/**
 * 应用常量定义
 * 集中管理应用中使用的各种常量
 */
object Constants {
    
    // 应用信息
    const val APP_NAME = "柠檬工具箱"
    const val APP_VERSION = "1.0.0"
    
    // 网络相关
    const val NETWORK_TIMEOUT = 30_000L // 30秒
    const val RETRY_COUNT = 3
    
    // 缓存相关
    const val CACHE_SIZE = 10 * 1024 * 1024L // 10MB
    const val CACHE_DURATION = 24 * 60 * 60 * 1000L // 24小时
    
    // 动画时长
    const val ANIMATION_DURATION_SHORT = 200L
    const val ANIMATION_DURATION_MEDIUM = 300L
    const val ANIMATION_DURATION_LONG = 500L
    
    // 界面相关
    const val BOTTOM_NAV_HEIGHT = 80
    const val CARD_ELEVATION = 4
    const val CORNER_RADIUS = 12
    
    // 搜索相关
    const val SEARCH_DEBOUNCE_DELAY = 300L
    const val MIN_SEARCH_LENGTH = 1
    
    // 分页相关
    const val PAGE_SIZE = 20
    const val PREFETCH_DISTANCE = 5
    
    // 文件相关
    const val MAX_FILE_SIZE = 5 * 1024 * 1024L // 5MB
    val ALLOWED_IMAGE_TYPES = arrayOf("jpg", "jpeg", "png", "gif", "webp")
    
    // 数据库相关
    const val DATABASE_NAME = "lemwood_tools.db"
    const val DATABASE_VERSION = 1
    
    // SharedPreferences键名
    object PreferenceKeys {
        const val THEME_MODE = "theme_mode"
        const val LANGUAGE = "language"
        const val FIRST_LAUNCH = "first_launch"
        const val LAST_UPDATE_CHECK = "last_update_check"
    }
    
    // 路由名称
    object Routes {
        const val HOME = "home"
        const val TOOLS = "tools"
        const val SEARCH = "search"
        const val SETTINGS = "settings"
        const val CALCULATOR = "calculator"
        const val CONVERTER = "converter"
        const val QR_CODE = "qrcode"
        const val TEXT_TOOLS = "text_tools"
        const val COLOR_PICKER = "color_picker"
        const val TIMER = "timer"
        const val WEATHER = "weather"
        const val NOTES = "notes"
    }
    
    // 错误消息
    object ErrorMessages {
        const val NETWORK_ERROR = "网络连接失败，请检查网络设置"
        const val UNKNOWN_ERROR = "发生未知错误，请稍后重试"
        const val FILE_NOT_FOUND = "文件未找到"
        const val PERMISSION_DENIED = "权限不足"
        const val INVALID_INPUT = "输入格式不正确"
    }
    
    // 成功消息
    object SuccessMessages {
        const val OPERATION_SUCCESS = "操作成功"
        const val FILE_SAVED = "文件保存成功"
        const val SETTINGS_SAVED = "设置保存成功"
    }
}