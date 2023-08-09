package com.subbaabhishek.newsapp

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.subbaabhishek.newsapp.databinding.ActivityMainBinding
import com.subbaabhishek.newsapp.presentation.adapter.NewsAdapter
import com.subbaabhishek.newsapp.presentation.viewmodel.NewsViewModel
import com.subbaabhishek.newsapp.presentation.viewmodel.NewsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    lateinit var viewModel: NewsViewModel
    @Inject
    lateinit var factory: NewsViewModelFactory
    @Inject
    lateinit var newsAdapter: NewsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, factory)[NewsViewModel::class.java]

        setSupportActionBar(binding.toolbar)
        setUpToolBar()
    }

    private fun setUpToolBar(){
        val toolbar : Toolbar? = findViewById(R.id.toolbar)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfig = AppBarConfiguration(navController.graph, binding.drawerLayout)
        if (toolbar != null) {
            toolbar.setupWithNavController(navController, appBarConfig)
            binding.navView.setupWithNavController(navController)
        }

    }

    override fun onBackPressed() {
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else{
            super.onBackPressed()
        }
    }
}