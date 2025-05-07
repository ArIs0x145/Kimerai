package io.github.aris0x145.kimerai.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.github.aris0x145.kimerai.BuildConfig
import timber.log.Timber

/**
 * 應用程式主類
 * 負責整合各個模塊並進行全局初始化
 */
@HiltAndroidApp
class KimeraiApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        // 初始化日誌系統
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        
        // 其他全局初始化
        initializeAppComponents()
    }
    
    /**
     * 初始化應用程式各個組件
     */
    private fun initializeAppComponents() {
        // 這裡可以放置不依賴於依賴注入的初始化代碼
        // 例如第三方庫的初始化等
        
        Timber.d("應用程式組件初始化完成")
    }
}