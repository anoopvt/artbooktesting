package com.anoopvt.artbooktesting.api

import com.anoopvt.artbooktesting.BuildConfig
import com.anoopvt.artbooktesting.util.Util.BASE_URL
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CustomOkHttpClient constructor(
    private val loggingInterceptor: LoggingInterceptor,
    private val customHttpLoggingInterceptor: CustomHttpLoggingInterceptor,
    private val connectivityInterceptor: ConnectivityInterceptor,
    private val headerInterceptor: HeaderInterceptor,
    private val sharedPreferenceDelegate: SharedPreferenceDelegate
) {

    private fun retrofitBuilder(okHttpClient: OkHttpClient, url: String): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofitBuilder by lazy {
        retrofitBuilder(getUserOkhttpClientWithHeader(), BASE_URL)
    }

    fun getUserOkhttpClientWithHeader(isHeaderNeedToPass: Boolean = true): OkHttpClient {
        val builder = OkHttpClient().newBuilder()
        builder.addInterceptor(connectivityInterceptor)
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(loggingInterceptor)
            builder.addInterceptor(customHttpLoggingInterceptor.getHttpLoggingInterceptor())
        }


        if (isHeaderNeedToPass) {
//            builder.addInterceptor(headerInterceptor)

            builder.authenticator(Authenticator { route, response ->
                val header = response.headers

                if (!response.isSuccessful && response.code == 401) {
                    val responseData =
                        retrofitBuilder.create(RetrofitAPI::class.java).getRefreshToken()
                            .execute()
                    @Suppress("SENSELESS_COMPARISON")
                    if (null != responseData && responseData.isSuccessful && null != responseData.body()) {
                        sharedPreferenceDelegate.setAccessToken(
                            responseData.body()?.token.toString()
                        )
                        sharedPreferenceDelegate.setRefreshToken(
                            responseData.body()?.refreshToken.toString()
                        )

                        return@Authenticator response.request.newBuilder()
                            .header(
                                "Authorization",
                                "Bearer ${sharedPreferenceDelegate.getAccessToken()}"
                            )
                            .header(
                                "X-Refresh-Token",
                                sharedPreferenceDelegate.getRefreshToken()
                                    ?: ""
                            ).build()
                    }
                    return@Authenticator null
                }
                return@Authenticator null
            })
        }
        return builder.build()
    }
}

