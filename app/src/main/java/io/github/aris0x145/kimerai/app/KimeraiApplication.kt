package io.github.aris0x145.kimerai.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * 應用程式主類
 */
@HiltAndroidApp
class KimeraiApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // 簡化的 onCreate，暫時不進行任何初始化
    }
}