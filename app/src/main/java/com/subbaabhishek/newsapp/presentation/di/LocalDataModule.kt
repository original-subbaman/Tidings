package com.subbaabhishek.newsapp.presentation.di

import com.subbaabhishek.newsapp.data.db.ArticleDAO
import com.subbaabhishek.newsapp.data.repository.datasource.NewsLocalDataSource
import com.subbaabhishek.newsapp.data.repository.datasourceimpl.NewsLocalDataSourceImpl

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {

    @Provides
    @Singleton
    fun providesLocalDataSource(articleDao: ArticleDAO) : NewsLocalDataSource{
        return NewsLocalDataSourceImpl(articleDao)
    }
}