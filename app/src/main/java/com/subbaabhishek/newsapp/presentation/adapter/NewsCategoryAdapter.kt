package com.subbaabhishek.newsapp.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.subbaabhishek.newsapp.data.model.Category
import com.subbaabhishek.newsapp.databinding.CategoryListItemLayoutBinding

class NewsCategoryAdapter() : RecyclerView.Adapter<NewsCategoryAdapter.NewsCategoryViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsCategoryViewHolder {
        val binding = CategoryListItemLayoutBinding
            .inflate(LayoutInflater.from(parent.context))
        return NewsCategoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return Category.CategoryList.size
    }

    override fun onBindViewHolder(holder: NewsCategoryViewHolder, position: Int) {
        holder.bind(Category.CategoryList[position])
    }

    inner class NewsCategoryViewHolder(private val binding : CategoryListItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(category : String){
            binding.categoryNameBtn.text = category
            binding.categoryNameBtn.setOnClickListener {
                Log.i("MYAPP", "Clicked")
            }
        }
    }

}