package com.example.dailyinspiration.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserPreferencesRepository(private val context: Context) {

    companion object {
        private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
        private val DAILY_NOTIFICATION_KEY = booleanPreferencesKey("daily_notification")
        private val LAST_QUOTE_ID_KEY = intPreferencesKey("last_quote_id")
    }

    val isDarkMode: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[DARK_MODE_KEY] ?: false
    }

    val isDailyNotificationEnabled: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[DAILY_NOTIFICATION_KEY] ?: false
    }

    val lastQuoteId: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[LAST_QUOTE_ID_KEY] ?: -1
    }

    suspend fun setDarkMode(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DARK_MODE_KEY] = enabled
        }
    }

    suspend fun setDailyNotification(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DAILY_NOTIFICATION_KEY] = enabled
        }
    }

    suspend fun setLastQuoteId(id: Int) {
        context.dataStore.edit { preferences ->
            preferences[LAST_QUOTE_ID_KEY] = id
        }
    }
}
