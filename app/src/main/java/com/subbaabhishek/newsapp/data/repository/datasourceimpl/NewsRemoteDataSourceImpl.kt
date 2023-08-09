package com.subbaabhishek.newsapp.data.repository.datasourceimpl

import com.subbaabhishek.newsapp.data.api.NewsAPIService
import com.subbaabhishek.newsapp.data.model.APIResponse
import com.subbaabhishek.newsapp.data.repository.datasource.NewsRemoteDataSource
import retrofit2.Response

class NewsRemoteDataSourceImpl(private  val newsAPIService: NewsAPIService) : NewsRemoteDataSource {
    override suspend fun getTopHeadlines(
        country : String, page : Int
    ): Response<APIResponse> {
        return newsAPIService.getTopHeadlines(country, page)
    }

    override suspend fun getSearchedTopHeadlines(
        country: String,
        page: Int,
        searchQuery: String
    ): Response<APIResponse> {
        return newsAPIService.getSearchedTopHeadlines(country, page, searchQuery)

    }
}