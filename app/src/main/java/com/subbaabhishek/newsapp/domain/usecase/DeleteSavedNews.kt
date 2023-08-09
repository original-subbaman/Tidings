package com.subbaabhishek.newsapp.domain.usecase

import com.subbaabhishek.newsapp.data.model.Article
import com.subbaabhishek.newsapp.domain.repository.NewsRepository

class DeleteSavedNews(private val newsRepository: NewsRepository) {

    suspend fun execute(article: Article) = newsRepository.deleteNews(article)
}