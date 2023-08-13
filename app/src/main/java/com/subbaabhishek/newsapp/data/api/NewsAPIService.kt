package com.subbaabhishek.newsapp.data.api

import com.subbaabhishek.newsapp.BuildConfig
import com.subbaabhishek.newsapp.data.model.APIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPIService {

    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country")
        country: String,
        @Query("page")
        page: Int,
        @Query("apiKey")
        apiKey: String = BuildConfig.API_KEY
    ) : Response<APIResponse>


    @GET("v2/top-headlines")
    suspend fun getSearchedTopHeadlines(
        @Query("country")
        country: String,
        @Query("page")
        page: Int,
        @Query("q")
        searchQuery: String,
        @Query("apiKey")
        apiKey: String = BuildConfig.API_KEY
    ) : Response<APIResponse>

    @GET("v2/top-headlines")
    suspend fun getNewsFromCategory(
        @Query("country")
        country: String,
        @Query("page")
        page: Int,
        @Query("category")
        category: String,
        @Query("apiKey")
        apiKey: String = BuildConfig.API_KEY
    ) : Response<APIResponse>

}