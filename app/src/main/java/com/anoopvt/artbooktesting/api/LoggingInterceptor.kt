package com.anoopvt.artbooktesting.api

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.internal.concurrent.TaskRunner.Companion.logger
import okio.IOException


class LoggingInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val t1 = System.nanoTime()
        logger.info(
            String.format(
                "Sending request %s on %s%n%s",
                request.url, chain.connection(), request.headers
            )
        )
        val response = chain.proceed(request)
        val t2 = System.nanoTime()
        logger.info(
            String.format(
                "Received response for %s in %.1fms%n%s",
                response.request.url, (t2 - t1) / 1e6, response.headers
            )
        )
        return response
    }
}