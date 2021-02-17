package com.weesnerDevelopment.app

import android.app.Application
import kimchi.Kimchi
import kimchi.logger.android.AdbWriter

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        Kimchi.addLog(AdbWriter())
    }
}