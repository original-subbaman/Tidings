package com.subbaabhishek.newsapp.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPreferenceRepository(
    private val dataStore : DataStore<Preferences>
) {

    private companion object {
        val COUNTRY_CODE = stringPreferencesKey("country_code")
        const val TAG = "UserPreferencesRepo"
    }

    suspend fun saveCountryCodePreferences(countryCode : String){
        dataStore.edit {
            it[COUNTRY_CODE] = countryCode
        }
    }

    fun getCountryCode() : Flow<String> = dataStore.data
        .catch {
            if(it is IOException){
                Log.e(TAG, "Error reading preferences", it)
                emit(emptyPreferences())
            }else{
                throw it
            }
        }
        .map {
        preferences ->
            preferences[COUNTRY_CODE] ?: ""
    }

}