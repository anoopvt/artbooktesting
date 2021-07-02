package com.anoopvt.artbooktesting.di

import com.anoopvt.artbooktesting.main.login.repo.LoginRepository
import com.anoopvt.artbooktesting.main.login.repo.LoginRepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {

    @Provides
    fun injectLoginRepository() =
        LoginRepository() as LoginRepositoryInterface


}