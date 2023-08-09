package com.subbaabhishek.newsapp.presentation.di

import com.subbaabhishek.newsapp.data.api.NewsAPIService
import com.subbaabhishek.newsapp.data.repository.datasource.NewsRemoteDataSource
import com.subbaabhishek.newsapp.data.repository.datasourceimpl.NewsRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {

    @Singleton
    @Provides
    fun providesNewsRemoteDataSource(newsAPIService: NewsAPIService) : NewsRemoteDataSource{
        return NewsRemoteDataSourceImpl(newsAPIService)
    }
}