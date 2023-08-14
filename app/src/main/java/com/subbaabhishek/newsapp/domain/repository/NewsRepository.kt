package com.subbaabhishek.newsapp.domain.repository

import com.subbaabhishek.newsapp.data.model.APIResponse
import com.subbaabhishek.newsapp.data.model.Article
import com.subbaabhishek.newsapp.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    suspend fun getNewsHeadlines(country : String, page : Int) : Resource<APIResponse>
    suspend fun getSearchedNews(country : String, page : Int, searchQuery : String) : Resource<APIResponse>
    suspend fun getNewsFromCategory(country: String, page: Int, category: String) : Resource<APIResponse>
    suspend fun saveNews(article : Article)
    suspend fun deleteNews(article : Article)
    fun getSavedNews() : Flow<List<Article>>


}