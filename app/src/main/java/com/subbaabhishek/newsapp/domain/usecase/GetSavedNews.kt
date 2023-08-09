package com.subbaabhishek.newsapp.domain.usecase

import com.subbaabhishek.newsapp.data.model.Article
import com.subbaabhishek.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetSavedNews(private val newsRepository: NewsRepository) {

    fun execute() : Flow<List<Article>>{
        return newsRepository.getSavedNews()
    }
}