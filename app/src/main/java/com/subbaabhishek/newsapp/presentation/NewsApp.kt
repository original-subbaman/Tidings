package com.subbaabhishek.newsapp.presentation
import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.subbaabhishek.newsapp.data.repository.UserPreferenceRepository
import dagger.hilt.android.HiltAndroidApp


private const val COUNTRY_CODE = "country_code"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = COUNTRY_CODE
)
@HiltAndroidApp
class NewsApp : Application(){

    lateinit var userPreferenceRepository: UserPreferenceRepository



    override fun onCreate() {
        super.onCreate()
        userPreferenceRepository = UserPreferenceRepository(dataStore)
    }
}