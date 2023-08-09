package com.subbaabhishek.newsapp.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.subbaabhishek.newsapp.data.model.APIResponse
import com.subbaabhishek.newsapp.data.model.Article
import com.subbaabhishek.newsapp.data.util.Resource
import com.subbaabhishek.newsapp.domain.usecase.DeleteSavedNews
import com.subbaabhishek.newsapp.domain.usecase.GetNewsHeadline
import com.subbaabhishek.newsapp.domain.usecase.GetSavedNews
import com.subbaabhishek.newsapp.domain.usecase.GetSearchedNews
import com.subbaabhishek.newsapp.domain.usecase.SaveNews
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception

class NewsViewModel(
    private val app: Application,
    private val getNewsHeadline: GetNewsHeadline,
    private val getSearchedNews: GetSearchedNews,
    private val saveNews: SaveNews,
    private val getSavedNews: GetSavedNews,
    private val deleteSavedNews: DeleteSavedNews,
) : AndroidViewModel(app) {
    val newsHeadLine : MutableLiveData<Resource<APIResponse>> = MutableLiveData()

    fun getNewsHeadline(country: String, page: Int) = viewModelScope.launch(Dispatchers.IO) {
        try{
            if(isNetworkAvailable(app)){
                newsHeadLine.postValue(Resource.Loading())
                val apiResult = getNewsHeadline.execute(country, page)
                newsHeadLine.postValue(apiResult)
            }else{
                newsHeadLine.postValue(Resource.Error("Internet is not available"))
            }
        }catch (e: Exception){
            newsHeadLine.postValue(Resource.Error(e.message.toString()))
        }


    }

    private fun isNetworkAvailable(context : Context?) : Boolean{
        if(context == null) return false;
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if(capabilities != null){
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        }
        return false
    }

    val searchList : MutableLiveData<Resource<APIResponse>> = MutableLiveData()

    fun getSearchedNews(country : String, page : Int, searchQuery : String) = viewModelScope.launch {
        searchList.postValue(Resource.Loading())
        try{
            if(isNetworkAvailable(app)){
                val response = getSearchedNews.execute(country, page, searchQuery)
                searchList.postValue(response)
            }else{
                searchList.postValue(Resource.Error("No internet connection"))
            }
        }catch (e: Exception){
            searchList.postValue(Resource.Error(e.message.toString()))
        }

    }

    fun saveArticle(article: Article) = viewModelScope.launch {
       saveNews.execute(article)
    }

    fun getSavedNews() = liveData {
        getSavedNews.execute().collect{
            emit(it)
        }
    }

    fun deleteArticle(article: Article) = viewModelScope.launch {
        deleteSavedNews.execute(article)
    }


}