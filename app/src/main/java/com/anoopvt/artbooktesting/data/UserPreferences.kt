package com.anoopvt.artbooktesting.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val EXAMPLE_COUNTER = intPreferencesKey("example_counter")
private val AUTH_TOKEN = stringPreferencesKey("auth_token")


class UserPreferences constructor(val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


    val exampleCounterFlow: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[EXAMPLE_COUNTER] ?: 0
        }

    val authTokenFlow: Flow<String?> = context.dataStore.data
        .map { token ->
            token[AUTH_TOKEN]
        }


    suspend fun saveAuthTokenFlow(token: String) {
        context.dataStore.edit { settings ->
            settings[AUTH_TOKEN] = token
        }
    }
}