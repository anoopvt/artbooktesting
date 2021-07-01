package com.anoopvt.artbooktesting.api

import android.content.Context
import android.content.SharedPreferences


class SharedPreferenceDelegate constructor(val context: Context){
    private val accessToken = "accessToken"
    private val refreshToken = "refreshToken"
    private val isClockedKey = "isClocked"
    private val loginResponseData = "loginResponseData"
    private val firstInsertionCompleteKey = "isFirstinsertionComplete"

    private fun getSharedPreference(): SharedPreferences {
        return context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    }

    fun setAccessToken(accessToken: String) =
        getSharedPreference().edit().putString(this.accessToken, accessToken).commit()

    fun getAccessToken(): String? = getSharedPreference().getString(accessToken, "")

    fun setRefreshToken(accessToken: String) =
        getSharedPreference().edit().putString(refreshToken, accessToken).commit()

    fun getRefreshToken(): String? = getSharedPreference().getString(refreshToken, "")

    fun clearSavedUserData(): Boolean {
        val userDataRemoved = getSharedPreference().edit().remove(loginResponseData).commit()
        return getSharedPreference().edit().clear().commit()
    }

    fun setLoginResponse(loginResponse: String): Boolean {
        return getSharedPreference().edit().putString(loginResponseData, loginResponse).commit()
    }

//    fun getLoginResponseData() =
//        Gson().fromJson(
//            getSharedPreference().getString(loginResponseData, ""),
//            UserLoginResponse::class.java
//        )

    fun setClockedIn(isClockedIn: Boolean) =
        getSharedPreference().edit().putBoolean(isClockedKey, isClockedIn).commit()

    fun getClockedIn() = getSharedPreference().getBoolean(isClockedKey, false)


    fun setInsertionForFirstTime(isFirstInsertionComplete: Boolean) =
        getSharedPreference().edit().putBoolean(
            firstInsertionCompleteKey,
            isFirstInsertionComplete
        ).commit()

    fun isDBInsertionComplete() = getSharedPreference().getBoolean(firstInsertionCompleteKey, false)
}

