package com.subbaabhishek.newsapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.subbaabhishek.newsapp.data.model.Article


@Database(
    entities = [Article::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(Converter::class)
abstract class ArticleDB : RoomDatabase() {
    abstract fun getArticleDao() : ArticleDAO

}