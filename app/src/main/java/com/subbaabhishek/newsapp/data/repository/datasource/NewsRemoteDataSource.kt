package com.subbaabhishek.newsapp.data.repository.datasource

import com.subbaabhishek.newsapp.data.model.APIResponse
import retrofit2.Response

interface NewsRemoteDataSource {

    suspend fun getTopHeadlines(country : String, page : Int) : Response<APIResponse>

    suspend fun getSearchedTopHeadlines(country : String, page : Int, searchQuery: String) : Response<APIResponse>

    suspend fun getNewsFromCategory(country: String, page: Int, category: String) : Response<APIResponse>

}