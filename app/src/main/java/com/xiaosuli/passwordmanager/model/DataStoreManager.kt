package com.xiaosuli.passwordmanager.model

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.xiaosuli.passwordmanager.App
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object DataStoreConstants {
    const val DATA_STORE_NAME = "settings"
    val STRING_KEY = stringPreferencesKey("string_key")
}

val Context.dataStore: DataStore<Preferences>
        by preferencesDataStore(DataStoreConstants.DATA_STORE_NAME)

class DataStoreRepository {

    suspend fun saveString(str: String) {
        App.context.dataStore.edit { preferences ->
            preferences[DataStoreConstants.STRING_KEY] = str
        }
    }

    val str: Flow<String> = App.context.dataStore.data
        .map { preferences ->
            preferences[DataStoreConstants.STRING_KEY] ?: ""
        }
}


