package com.anoopvt.artbooktesting.api

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor constructor(private val sharedPreferences: SharedPreferenceDelegate) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request =
            chain.request()
                .newBuilder()
                .addHeader(
                    "Authorization",
                    "Bearer ${sharedPreferences.getAccessToken() ?: ""}"
                )
                .addHeader("Client", "app")
                .addHeader("X-Refresh-Token", sharedPreferences.getRefreshToken() ?: "")
                .addHeader("Accept-Language", "1")
                .build()
        return chain.proceed(request)
    }
}