package com.anoopvt.artbooktesting.data

import com.anoopvt.artbooktesting.repo.UserRepositoryInterface
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(private val userPreferences: UserPreferences) :
    UserRepositoryInterface {

    override suspend fun saveToken(token: String) {
        userPreferences.saveAuthTokenFlow(token)
    }

    override fun getToken(): Flow<String?> = userPreferences.authTokenFlow

}