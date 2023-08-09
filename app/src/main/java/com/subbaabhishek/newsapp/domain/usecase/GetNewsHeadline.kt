package com.subbaabhishek.newsapp.domain.usecase

import com.subbaabhishek.newsapp.data.model.APIResponse
import com.subbaabhishek.newsapp.data.util.Resource
import com.subbaabhishek.newsapp.domain.repository.NewsRepository

class GetNewsHeadline(private val newsRepository: NewsRepository) {

    suspend fun execute(country : String, page : Int) : Resource<APIResponse>{
        return newsRepository.getNewsHeadlines(country, page)
    }
}