package com.subbaabhishek.newsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.subbaabhishek.newsapp.databinding.FragmentNewsBinding
import com.subbaabhishek.newsapp.presentation.adapter.CategoryItemDecoration
import com.subbaabhishek.newsapp.presentation.adapter.NewsAdapter
import com.subbaabhishek.newsapp.presentation.adapter.NewsCategoryAdapter
import com.subbaabhishek.newsapp.presentation.util.CountryCodeMap
import com.subbaabhishek.newsapp.presentation.util.SelectCountryAlertDialog
import com.subbaabhishek.newsapp.presentation.viewmodel.NewsViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NewsFragment : Fragment() {

    private lateinit var viewModel: NewsViewModel
    private lateinit var fragmentNewsBinding: FragmentNewsBinding
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var categoryAdapter: NewsCategoryAdapter
    private lateinit var countries: List<String>
    private var isoCountryCode = "us"
    private var country = "United States"
    private var page = 1
    private var isScrolling = false
    private var isLoading = false
    private var isLastPage = false
    private var pages = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener("index") { _, bundle ->
            val countryFromBundle = countries[bundle.getInt("index_country")]
            if (countryFromBundle.isNotEmpty()) {
                isoCountryCode =
                    CountryCodeMap.isoCodeToNameMap.getOrDefault(countryFromBundle, isoCountryCode)
            }
            country = countryFromBundle
            page = 1
            viewModel.getNewsHeadline(isoCountryCode, page)
            requireActivity().invalidateOptionsMenu()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentNewsBinding = FragmentNewsBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        countries = resources.getStringArray(R.array.country_array).toList()

        initNewsCategoryRecyclerAdapter()
        initCategoryRecyclerView()
        initNewsRecyclerAdapter()
        initNewsRecyclerView()
        viewModel.getNewsHeadline(isoCountryCode, page)
        viewTopNewsList()
        setSearchView()
        setUpBottomNavView()

    }

    private fun initNewsCategoryRecyclerAdapter() {
        categoryAdapter = (activity as MainActivity).categoryAdapter
        categoryAdapter.setOnItemClickListener {
            if(it == "all"){
                viewModel.getNewsHeadline(isoCountryCode, page)
            }else{
                viewModel.getNewsFromCategory(isoCountryCode, page, it)
            }
            viewTopNewsList()
        }
    }

    private fun initCategoryRecyclerView() {
        fragmentNewsBinding.rvCategory.apply {
            adapter = categoryAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
            addItemDecoration(CategoryItemDecoration())
        }
    }

    private fun initNewsRecyclerAdapter() {
        newsAdapter = (activity as MainActivity).newsAdapter
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("selected_article", it)
            }
            findNavController()
                .navigate(
                    R.id.action_newsFragment_to_infoFragment,
                    bundle
                )
        }
    }

    private fun initNewsRecyclerView() {
        fragmentNewsBinding.rvNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(onScrollListener)
        }
    }

    private fun viewTopNewsList() {
        viewModel.newsHeadLine.observe(
            viewLifecycleOwner
        ) { response ->
            when (response) {
                is com.subbaabhishek.newsapp.data.util.Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        if (it.articles.toList().isEmpty()) {
                            fragmentNewsBinding.btnNextPage.isEnabled = false
                            fragmentNewsBinding.rvNews.visibility = View.GONE
                            fragmentNewsBinding.emptyNewsText.visibility = View.VISIBLE
                        } else {
                            fragmentNewsBinding.emptyNewsText.visibility = View.GONE
                            fragmentNewsBinding.rvNews.visibility = View.VISIBLE
                            fragmentNewsBinding.btnNextPage.isEnabled = true

                            newsAdapter.differ.submitList(it.articles.toList())
                            pages = if (it.totalResults % 20 == 0) {
                                it.totalResults / 20
                            } else {
                                it.totalResults / 20 + 1
                            }

                            isLastPage = page == pages

                        }


                    }
                }

                is com.subbaabhishek.newsapp.data.util.Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(activity, "An error occurred: $it", Toast.LENGTH_LONG).show()
                    }
                }

                is com.subbaabhishek.newsapp.data.util.Resource.Loading -> {
                    showProgressBar()

                }
            }
        }
    }

    private fun setUpBottomNavView() {
        fragmentNewsBinding.btnNextPage.setOnClickListener {
            nextPage()
        }
        fragmentNewsBinding.btnPrevPage.setOnClickListener {
            prevPage()
        }
    }

    private fun showProgressBar() {
        isLoading = true
        fragmentNewsBinding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        isLoading = false
        fragmentNewsBinding.progressBar.visibility = View.GONE
    }

    private fun setSearchView() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.app_bar_menu, menu)
                val searchView: SearchView = menu.findItem(R.id.search).actionView as SearchView
                searchView.queryHint = "Search news headlines..."
                searchView.maxWidth = Integer.MAX_VALUE

                Log.i("MYAPP", "Menu create")

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(p0: String?): Boolean {
                        viewModel.getSearchedNews("us", page = page, p0.toString())
                        viewSearchedNews()
                        return false
                    }

                    override fun onQueryTextChange(p0: String?): Boolean {
                        MainScope().launch {
                            delay(2000)
                            viewModel.getSearchedNews("us", page = page, p0.toString())
                            viewSearchedNews()
                        }

                        return false
                    }

                })
                searchView.setOnCloseListener {
                    initNewsRecyclerView()
                    viewTopNewsList()
                    false
                }


            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.search -> {
                        true
                    }

                    R.id.country -> {
                        //display select box
                        SelectCountryAlertDialog().show(parentFragmentManager, "Alert Dialog")
                        true
                    }

                    else -> false
                }
            }

            override fun onPrepareMenu(menu: Menu) {
                super.onPrepareMenu(menu)
                Log.i("myAPP", "invalidated")
                val isoCountryCode = CountryCodeMap.isoCodeToNameMap[country]
                if (isoCountryCode != null) {
                    menu.findItem(R.id.country).title = isoCountryCode.uppercase()
                }

            }
        }, viewLifecycleOwner, androidx.lifecycle.Lifecycle.State.RESUMED)
    }

    private fun viewSearchedNews() {
        viewModel.searchList.observe(
            viewLifecycleOwner
        ) { response ->
            when (response) {
                is com.subbaabhishek.newsapp.data.util.Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles.toList())
                        pages = if (it.totalResults % 20 == 0) {
                            it.totalResults / 20
                        } else {
                            it.totalResults / 20 + 1
                        }

                        isLastPage = page == pages
                    }
                }

                is com.subbaabhishek.newsapp.data.util.Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(activity, "An error occurred: $it", Toast.LENGTH_LONG).show()
                    }
                }

                is com.subbaabhishek.newsapp.data.util.Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun nextPage() {
        page++
        viewModel.getNewsHeadline(isoCountryCode, page)
        setPageNumberText()
    }

    private fun prevPage() {
        page = if (page == 1) page else page - 1
        viewModel.getNewsHeadline(isoCountryCode, page)
        setPageNumberText()
    }

    private fun setPageNumberText() {
        fragmentNewsBinding.pageTextView.text =
            resources.getString(R.string.page_number, page.toString())
        Log.i("MyApp", "${resources.getString(R.string.page_number, page.toString())}")

    }


    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = fragmentNewsBinding.rvNews.layoutManager as LinearLayoutManager
            val sizeOfTheCurrentList = layoutManager.itemCount
            val visibleItems = layoutManager.childCount
            val topPosition = layoutManager.findFirstVisibleItemPosition()

            val hasReachedToEnd = topPosition + visibleItems >= sizeOfTheCurrentList
            val shouldPaginate = !isLoading && !isLastPage && hasReachedToEnd && isScrolling

            if (shouldPaginate) {
                nextPage()
                isScrolling = false
            }

        }
    }


}