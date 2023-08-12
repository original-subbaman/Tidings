package com.subbaabhishek.newsapp.domain.usecase

import com.subbaabhishek.newsapp.data.model.APIResponse
import com.subbaabhishek.newsapp.data.util.Resource
import com.subbaabhishek.newsapp.domain.repository.NewsRepository

class GetNewsOfCategory(private val newsRepository: NewsRepository) {

    suspend fun execute(country: String, page: Int, category: String) : Resource<APIResponse> {
        return newsRepository.getNewsFromCategory(country, page, category)
    }

}