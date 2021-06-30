package com.anoopvt.artbooktesting.api

import com.anoopvt.artbooktesting.model.ImageResponse
import com.anoopvt.artbooktesting.model.RefreshTokenResponseModel
import com.anoopvt.artbooktesting.util.Util.API_KEY
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RetrofitAPI {

    @GET("/api/")
    suspend fun imageSearch(
        @Query("q") searchQuery: String,
        @Query("key") apiKey:String = API_KEY
    ):Response<ImageResponse>

    @POST("refresh")
     fun getRefreshToken(): Call<RefreshTokenResponseModel>



}