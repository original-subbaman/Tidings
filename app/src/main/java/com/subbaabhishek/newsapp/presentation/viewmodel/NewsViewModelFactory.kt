package com.subbaabhishek.newsapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.subbaabhishek.newsapp.domain.usecase.DeleteSavedNews
import com.subbaabhishek.newsapp.domain.usecase.GetNewsHeadline
import com.subbaabhishek.newsapp.domain.usecase.GetSavedNews
import com.subbaabhishek.newsapp.domain.usecase.GetSearchedNews
import com.subbaabhishek.newsapp.domain.usecase.SaveNews

class NewsViewModelFactory(
    private val app : Application,
    private val getNewsHeadline: GetNewsHeadline,
    private val getSearchedNews: GetSearchedNews,
    private val saveNews: SaveNews,
    private val getSavedNews: GetSavedNews,
    private val deleteSavedNews: DeleteSavedNews,

) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass : Class<T>) : T {
        return NewsViewModel(
            app,
            getNewsHeadline,
            getSearchedNews,
            saveNews,
            getSavedNews,
            deleteSavedNews
        ) as T
    }
}