package com.anoopvt.artbooktesting

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ArtBookApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    companion object {
        private var INSTANCE: ArtBookApplication? = null
        fun getAppContext(): Context {
            return INSTANCE!!.applicationContext
        }
    }
}