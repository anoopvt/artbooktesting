package com.anoopvt.artbooktesting.main.login.repo

import androidx.lifecycle.LiveData
import com.anoopvt.artbooktesting.main.login.model.UserModel

class LoginRepository : LoginRepositoryInterface {
    override suspend fun insertUser(user: UserModel) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser(user: UserModel) {
        TODO("Not yet implemented")
    }

    override fun getUser(): LiveData<UserModel> {
        TODO("Not yet implemented")
    }
}