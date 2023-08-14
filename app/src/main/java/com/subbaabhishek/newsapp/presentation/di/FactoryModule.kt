package com.subbaabhishek.newsapp.presentation.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.subbaabhishek.newsapp.data.repository.UserPreferenceRepository

import com.subbaabhishek.newsapp.domain.usecase.DeleteSavedNews
import com.subbaabhishek.newsapp.domain.usecase.GetNewsHeadline
import com.subbaabhishek.newsapp.domain.usecase.GetNewsFromCategory
import com.subbaabhishek.newsapp.domain.usecase.GetSavedNews
import com.subbaabhishek.newsapp.domain.usecase.GetSearchedNews
import com.subbaabhishek.newsapp.domain.usecase.SaveNews
import com.subbaabhishek.newsapp.presentation.viewmodel.NewsViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

    @Provides
    @Singleton
    fun providesViewModelFactory(
        application: Application,
        getNewsHeadline: GetNewsHeadline,
        getSearchedNews: GetSearchedNews,
        saveNews: SaveNews,
        getSavedNews: GetSavedNews,
        deleteSavedNews: DeleteSavedNews,
        getNewsFromCategory: GetNewsFromCategory,
    ): NewsViewModelFactory {
        return NewsViewModelFactory(
            application,
            getNewsHeadline,
            getSearchedNews,
            saveNews,
            getSavedNews,
            deleteSavedNews,
            getNewsFromCategory
        )
    }

    @Provides
    @Singleton
    fun providesDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            produceFile = { context.preferencesDataStoreFile("user_preferences") }
        )
    }

    @Provides
    @Singleton
    fun providesUserPreferences(dataStore: DataStore<Preferences>) = UserPreferenceRepository(dataStore)
}