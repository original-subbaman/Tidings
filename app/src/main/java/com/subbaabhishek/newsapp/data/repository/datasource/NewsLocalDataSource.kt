package com.subbaabhishek.newsapp.data.repository.datasource

import com.subbaabhishek.newsapp.data.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsLocalDataSource {

    suspend fun saveArticle(article: Article)

    fun getSavedArticles() : Flow<List<Article>>

    suspend fun deleteArticle(article: Article)
}