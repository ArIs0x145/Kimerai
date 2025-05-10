package io.github.aris0x145.kimerai.core.common.logging

import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 日誌初始化器，用於設置 Timber
 */
object LoggerInitializer {
    fun init(isDebug: Boolean) {
        if (isDebug) {
            Timber.plant(Timber.DebugTree())
        }
        // 在生產環境中可以添加自定義的 Tree
        // 例如，將錯誤上報到分析服務
    }
}

/**
 * 日誌記錄器接口，提供應用程式中統一的日誌功能
 */
interface Logger {
    fun debug(message: String, vararg args: Any)
    fun info(message: String, vararg args: Any)
    fun warning(message: String, vararg args: Any)
    fun error(message: String, vararg args: Any)
    fun error(throwable: Throwable, message: String, vararg args: Any)
}

/**
 * 基於 Timber 的日誌實現
 */
@Singleton
class TimberLogger @Inject constructor() : Logger {
    
    override fun debug(message: String, vararg args: Any) {
        Timber.d(message, *args)
    }
    
    override fun info(message: String, vararg args: Any) {
        Timber.i(message, *args)
    }
    
    override fun warning(message: String, vararg args: Any) {
        Timber.w(message, *args)
    }
    
    override fun error(message: String, vararg args: Any) {
        Timber.e(message, *args)
    }
    
    override fun error(throwable: Throwable, message: String, vararg args: Any) {
        Timber.e(throwable, message, *args)
    }
}
