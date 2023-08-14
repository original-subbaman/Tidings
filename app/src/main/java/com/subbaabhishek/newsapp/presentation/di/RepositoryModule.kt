package com.subbaabhishek.newsapp.presentation.di

import com.subbaabhishek.newsapp.data.repository.NewsRepositoryImpl
import com.subbaabhishek.newsapp.data.repository.datasource.NewsLocalDataSource
import com.subbaabhishek.newsapp.data.repository.datasource.NewsRemoteDataSource
import com.subbaabhishek.newsapp.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {


    @Singleton
    @Provides
    fun providesNewsRepository(
        newsRemoteDataSource : NewsRemoteDataSource,
        newsLocalDataSource: NewsLocalDataSource,
    ) : NewsRepository{

        return NewsRepositoryImpl(newsRemoteDataSource, newsLocalDataSource)

    }
}