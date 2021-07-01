package com.anoopvt.artbooktesting

import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp(MultiDexApplication::class)
class MyApplication : Hilt_MyApplication()