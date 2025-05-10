package io.github.aris0x145.kimerai.core.common.constants

/**
 * 應用程式全局常量
 */
object AppConstants {
    // 應用基本設置
    const val APP_NAME = "Kimerai"
    const val DEFAULT_TIMEOUT_MS = 30000L
    
    // 數據存儲相關
    const val DATABASE_NAME = "kimerai_database"
    const val PREFERENCES_NAME = "kimerai_preferences"
    
    // 功能相關常量
    const val MAX_MESSAGE_LENGTH = 10000
    const val MAX_CONVERSATIONS = 100
    
    // 日誌標籤前綴
    const val LOG_TAG_PREFIX = "Kimerai_"
    
    // 螢幕路由
    object Routes {
        const val CONVERSATION = "conversation"
        const val CHAT_HISTORY = "chat_history"
        const val SETTINGS = "settings"
        const val TOOL_HUB = "tool_hub"
    }
}
