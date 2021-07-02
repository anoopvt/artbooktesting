package com.anoopvt.artbooktesting.main.login.repo

import androidx.lifecycle.LiveData
import com.anoopvt.artbooktesting.main.login.model.UserModel

interface LoginRepositoryInterface {

    suspend fun insertUser(user: UserModel)

    suspend fun deleteUser(user: UserModel)

    fun getUser(): LiveData<UserModel>

}