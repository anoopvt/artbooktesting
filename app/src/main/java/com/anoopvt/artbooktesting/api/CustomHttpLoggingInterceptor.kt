package com.anoopvt.artbooktesting.api

import okhttp3.logging.HttpLoggingInterceptor

class CustomHttpLoggingInterceptor {
    fun getHttpLoggingInterceptor():HttpLoggingInterceptor{
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }
}