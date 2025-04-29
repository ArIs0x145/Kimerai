package io.github.aris0x145.kimerai

import android.app.Application
import dagger.hilt.android.HiltAndroidApp // Import Hilt annotation

@HiltAndroidApp // Add Hilt annotation
class KimeraiApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialization code here if needed
    }
}
