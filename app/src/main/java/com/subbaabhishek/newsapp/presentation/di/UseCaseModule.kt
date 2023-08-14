package com.subbaabhishek.newsapp.presentation.di

import com.subbaabhishek.newsapp.domain.repository.NewsRepository
import com.subbaabhishek.newsapp.domain.usecase.DeleteSavedNews
import com.subbaabhishek.newsapp.domain.usecase.GetNewsHeadline
import com.subbaabhishek.newsapp.domain.usecase.GetNewsFromCategory
import com.subbaabhishek.newsapp.domain.usecase.GetSavedNews
import com.subbaabhishek.newsapp.domain.usecase.GetSearchedNews
import com.subbaabhishek.newsapp.domain.usecase.SaveNews
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun providesNewsHeadLineUseCase(
        newsRepository: NewsRepository
    ): GetNewsHeadline {
        return GetNewsHeadline(newsRepository)
    }

    @Provides
    @Singleton
    fun providesSearchedNewsUseCase(
        newsRepository: NewsRepository
    ): GetSearchedNews {
        return GetSearchedNews(newsRepository)
    }

    @Provides
    @Singleton
    fun providesSaveNewsUseCase(
        newsRepository: NewsRepository
    ): SaveNews {
        return SaveNews(newsRepository)
    }

    @Provides
    @Singleton
    fun providesGetSavedNews(
        newsRepository: NewsRepository
    ): GetSavedNews {
        return GetSavedNews(newsRepository)
    }

    @Provides
    @Singleton
    fun providesDeleteNewsUseCase(
        newsRepository: NewsRepository
    ): DeleteSavedNews {
        return DeleteSavedNews(newsRepository)
    }

    @Provides
    @Singleton
    fun providesGetNewsOfCategory(
        newsRepository: NewsRepository
    ): GetNewsFromCategory {
        return GetNewsFromCategory(newsRepository)
    }

}