package com.subbaabhishek.newsapp.presentation.di

import android.app.Application
import com.subbaabhishek.newsapp.domain.usecase.DeleteSavedNews
import com.subbaabhishek.newsapp.domain.usecase.GetNewsHeadline
import com.subbaabhishek.newsapp.domain.usecase.GetSavedNews
import com.subbaabhishek.newsapp.domain.usecase.GetSearchedNews
import com.subbaabhishek.newsapp.domain.usecase.SaveNews
import com.subbaabhishek.newsapp.presentation.viewmodel.NewsViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    ) : NewsViewModelFactory{
        return NewsViewModelFactory(application, getNewsHeadline, getSearchedNews, saveNews, getSavedNews, deleteSavedNews)
    }
}