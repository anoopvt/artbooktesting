package com.anoopvt.artbooktesting.di

import android.content.Context
import androidx.room.Room
import com.anoopvt.artbooktesting.R
import com.anoopvt.artbooktesting.api.*
import com.anoopvt.artbooktesting.repo.ArtRepository
import com.anoopvt.artbooktesting.repo.ArtRepositoryInterface
import com.anoopvt.artbooktesting.roomdb.ArtDao
import com.anoopvt.artbooktesting.roomdb.ArtDatabase
import com.anoopvt.artbooktesting.util.Util.BASE_URL
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal object AppModule {

    @Singleton
    @Provides
    fun injectRoomDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, ArtDatabase::class.java, "ArtBookDB"
    ).build()

    @Singleton
    @Provides
    fun injectArtDao(database: ArtDatabase) = database.artDao()

    @Singleton
    @Provides
    fun injectConnectivityInterceptor(@ApplicationContext context: Context) =
        ConnectivityInterceptor(context)


    @Singleton
    @Provides
    fun injectSharedPreferenceDelegate(@ApplicationContext context: Context) =
        SharedPreferenceDelegate(context)


    @Singleton
    @Provides
    fun injectHeaderInterceptor(sharedPreferenceDelegate:SharedPreferenceDelegate) = HeaderInterceptor(sharedPreferenceDelegate)


    @Singleton
    @Provides
    fun injectCustomOkHttpClient(connectivityInterceptor: ConnectivityInterceptor,headerInterceptor:HeaderInterceptor,sharedPreferenceDelegate: SharedPreferenceDelegate) =
        CustomOkHttpClient(
            LoggingInterceptor(),
            CustomHttpLoggingInterceptor(),
            connectivityInterceptor,
            headerInterceptor,
            sharedPreferenceDelegate
        )

    @Singleton
    @Provides
    fun injectOkHttpClient(customOkHttpClient: CustomOkHttpClient): OkHttpClient {
        return customOkHttpClient.getUserOkhttpClientWithHeader()
    }

    @Singleton
    @Provides
    fun injectRetrofitAPI(okHttpClient: OkHttpClient): RetrofitAPI {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(RetrofitAPI::class.java)
    }

    @Singleton
    @Provides
    fun injectNormalArtRepo(dao: ArtDao, api: RetrofitAPI) =
        ArtRepository(dao, api) as ArtRepositoryInterface


    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context) = Glide.with(context)
        .setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
        )

}