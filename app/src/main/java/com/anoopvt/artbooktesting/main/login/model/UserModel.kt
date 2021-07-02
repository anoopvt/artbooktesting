package com.anoopvt.artbooktesting.main.login.model

import androidx.room.Entity

@Entity(tableName = "user")
data class UserModel(
    var name: String,
    val username: String

)