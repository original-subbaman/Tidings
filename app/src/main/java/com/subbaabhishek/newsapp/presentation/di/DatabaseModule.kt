package com.subbaabhishek.newsapp.presentation.di

import android.app.Application
import androidx.room.Room
import com.subbaabhishek.newsapp.data.db.ArticleDAO
import com.subbaabhishek.newsapp.data.db.ArticleDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun providesNewsDB(app: Application) : ArticleDB{
        return Room.databaseBuilder(app, ArticleDB::class.java, "news_db")
            .fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun providesNewsDao(articleDb : ArticleDB) : ArticleDAO{
        return articleDb.getArticleDao()
    }
}