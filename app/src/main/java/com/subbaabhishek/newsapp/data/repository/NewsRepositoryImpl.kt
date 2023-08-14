package com.subbaabhishek.newsapp.data.repository

import com.subbaabhishek.newsapp.data.model.APIResponse
import com.subbaabhishek.newsapp.data.model.Article
import com.subbaabhishek.newsapp.data.repository.datasource.NewsLocalDataSource
import com.subbaabhishek.newsapp.data.repository.datasource.NewsRemoteDataSource
import com.subbaabhishek.newsapp.data.util.Resource
import com.subbaabhishek.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepositoryImpl(
    private val newsRemoteDataSource : NewsRemoteDataSource,
    private val newsLocalDataSource: NewsLocalDataSource,
) : NewsRepository{

    override suspend fun getNewsHeadlines(country : String, page : Int): Resource<APIResponse> {
        return responseToResource(newsRemoteDataSource.getTopHeadlines(country, page))
    }

    override suspend fun getSearchedNews(
        country: String,
        page: Int,
        searchQuery: String
    ): Resource<APIResponse> {
        return responseToResource(
            newsRemoteDataSource.getSearchedTopHeadlines(country, page, searchQuery)
        )
    }

    override suspend fun getNewsFromCategory(
        country: String,
        page: Int,
        category: String
    ): Resource<APIResponse> {
        return responseToResource(
            newsRemoteDataSource.getNewsFromCategory(country, page, category)
        )
    }

    private fun responseToResource(response : Response<APIResponse>) : Resource<APIResponse>{
        if(response.isSuccessful){
            response.body()?.let{
                result -> return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }


    override suspend fun saveNews(article: Article) {
        newsLocalDataSource.saveArticle(article)
    }

    override suspend fun deleteNews(article: Article) {
        newsLocalDataSource.deleteArticle(article)
    }

    override fun getSavedNews(): Flow<List<Article>> {
        return newsLocalDataSource.getSavedArticles()
    }




}