package com.anoopvt.artbooktesting.di

import android.content.Context
import androidx.room.Room
import com.anoopvt.artbooktesting.R
import com.anoopvt.artbooktesting.api.RetrofitAPI
import com.anoopvt.artbooktesting.repo.ArtRepository
import com.anoopvt.artbooktesting.repo.ArtRepositoryInterface
import com.anoopvt.artbooktesting.repo.FakeArtRepositoryTest
import com.anoopvt.artbooktesting.roomdb.ArtDao
import com.anoopvt.artbooktesting.roomdb.ArtDatabase
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Named
import javax.inject.Singleton


@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
object TestAppModule {

    @Provides
    @Named("testDatabase")
    fun injectInMemoryRoom(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context, ArtDatabase::class.java)
            .allowMainThreadQueries()
            .build()

    @Singleton
    @Provides
    fun injectNormalArtRepo() =
        FakeArtRepositoryTest() as ArtRepositoryInterface

    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context) = Glide.with(context)
        .setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
        )


}