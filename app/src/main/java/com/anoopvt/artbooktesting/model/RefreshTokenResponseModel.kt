package com.anoopvt.artbooktesting.model


import com.google.gson.annotations.SerializedName

data class RefreshTokenResponseModel(
    @SerializedName("refreshToken")
    var refreshToken: String,
    @SerializedName("token")
    var token: String
)