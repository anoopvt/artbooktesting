package com.anoopvt.artbooktesting.repo

import kotlinx.coroutines.flow.Flow

interface UserRepositoryInterface {

    suspend fun saveToken(token:String)
    fun getToken(): Flow<String?>
}