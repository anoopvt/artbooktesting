package com.anoopvt.artbooktesting.api

import com.anoopvt.artbooktesting.BuildConfig
import okhttp3.*
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class MyOkHttpClient constructor(
    private val headerInterceptor: HeaderInterceptor,
    private val connectivityInterceptor: ConnectivityInterceptor,
    private val httpLoggingInterceptor: LoggingInterceptor
) {

    fun getUserOkhttpClientWithHeader(isHeaderNeedToPass: Boolean = true): OkHttpClient {
        val builder = OkHttpClient().newBuilder()

        builder.addInterceptor(connectivityInterceptor)

        if (isHeaderNeedToPass) {
            builder.addInterceptor(headerInterceptor)
            builder.readTimeout(300, TimeUnit.SECONDS)
            builder.writeTimeout(300, TimeUnit.SECONDS)
            builder.connectTimeout(300, TimeUnit.SECONDS)

            //todo This will accept all the certificate. Basically not  safe :)
            val trustAllCerts = getTrustAllCerts()
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())
            val sslSocketFactory = sslContext.socketFactory
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier(HostnameVerifier { p0, p1 -> true })
            /*-----------------End of the certificate---------------------*/

            builder.authenticator(object : Authenticator {
                override fun authenticate(route: Route?, response: Response): Request? {
                    val header = response.headers

//                    val responseData = retrofitBuilder.create(APIintefaces::class.java).getRefreshToken().execute()

//                    if (null != responseData && responseData.isSuccessful && null != responseData.body()) {
//                        SharedPreferenceDelegate.setAccessToken(responseData.body()?.token.toString())
//                        SharedPreferenceDelegate.setRefreshToken(responseData.body()?.refreshToken.toString())
//                        return response.request.newBuilder()
//                            .header(
//                                "Authorization",
//                                "Bearer ${SharedPreferenceDelegate.getAccessToken()}"
//                            )
//                            .header(
//                                "X-Refresh-Token",
//                                SharedPreferenceDelegate.getRefreshToken() ?: ""
//                            ).build()
//                    }
                    return null
                }
            })
        }
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(httpLoggingInterceptor)
        }
        return builder.build()
    }

    /**
     * For accepting all the certificate given
     * */
    fun getTrustAllCerts(): Array<TrustManager> {
        return arrayOf(object : X509TrustManager {
            @Throws(CertificateException::class)
            override fun checkClientTrusted(
                chain: Array<X509Certificate>,
                authType: String
            ) {
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(
                chain: Array<X509Certificate>,
                authType: String
            ) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        })
    }
}