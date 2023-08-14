package com.subbaabhishek.newsapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.subbaabhishek.newsapp.data.repository.UserPreferenceRepository
import com.subbaabhishek.newsapp.domain.usecase.DeleteSavedNews
import com.subbaabhishek.newsapp.domain.usecase.GetNewsHeadline
import com.subbaabhishek.newsapp.domain.usecase.GetNewsFromCategory
import com.subbaabhishek.newsapp.domain.usecase.GetSavedNews
import com.subbaabhishek.newsapp.domain.usecase.GetSearchedNews
import com.subbaabhishek.newsapp.domain.usecase.SaveNews
import com.subbaabhishek.newsapp.presentation.NewsApp

class NewsViewModelFactory(
    private val app : Application,
    private val getNewsHeadline: GetNewsHeadline,
    private val getSearchedNews: GetSearchedNews,
    private val saveNews: SaveNews,
    private val getSavedNews: GetSavedNews,
    private val deleteSavedNews: DeleteSavedNews,
    private val getNewsFromCategory: GetNewsFromCategory,
    ) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass : Class<T>) : T {
        val application = (app as NewsApp)
        return NewsViewModel(
            app,
            getNewsHeadline,
            getSearchedNews,
            saveNews,
            getSavedNews,
            deleteSavedNews,
            getNewsFromCategory,
            application.userPreferenceRepository,
        ) as T
    }
}