package com.subbaabhishek.newsapp.domain.usecase

import com.subbaabhishek.newsapp.data.model.Article
import com.subbaabhishek.newsapp.domain.repository.NewsRepository

class SaveNews(private val newsRepository: NewsRepository) {

    suspend fun execute(article: Article) = newsRepository.saveNews(article)

}