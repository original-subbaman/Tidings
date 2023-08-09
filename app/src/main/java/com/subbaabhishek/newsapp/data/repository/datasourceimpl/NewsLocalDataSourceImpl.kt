package com.subbaabhishek.newsapp.data.repository.datasourceimpl

import com.subbaabhishek.newsapp.data.db.ArticleDAO
import com.subbaabhishek.newsapp.data.model.Article
import com.subbaabhishek.newsapp.data.repository.datasource.NewsLocalDataSource
import kotlinx.coroutines.flow.Flow

class NewsLocalDataSourceImpl(
    private val articleDAO: ArticleDAO
) : NewsLocalDataSource {

    override suspend fun saveArticle(article: Article) {
        articleDAO.insert(article)
    }

    override fun getSavedArticles(): Flow<List<Article>> {
        return articleDAO.getAllArticles()
    }

    override suspend fun deleteArticle(article: Article) {
        articleDAO.deleteArticle(article)
    }
}