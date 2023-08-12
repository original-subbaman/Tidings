package com.subbaabhishek.newsapp.presentation.di

import com.subbaabhishek.newsapp.presentation.adapter.NewsAdapter
import com.subbaabhishek.newsapp.presentation.adapter.NewsCategoryAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {

    @Provides
    @Singleton
    fun providesNewsAdapter(): NewsAdapter {
        return NewsAdapter()
    }

    @Provides
    @Singleton
    fun providesNewsCategoryAdapter(): NewsCategoryAdapter {
        return NewsCategoryAdapter()
    }
}