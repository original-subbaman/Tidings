package com.subbaabhishek.newsapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.subbaabhishek.newsapp.R
import com.subbaabhishek.newsapp.data.model.Category
import com.subbaabhishek.newsapp.databinding.CategoryListItemLayoutBinding

class NewsCategoryAdapter() : RecyclerView.Adapter<NewsCategoryAdapter.NewsCategoryViewHolder>(){


    private var selectedItemPosition = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsCategoryViewHolder {
        val binding = CategoryListItemLayoutBinding
            .inflate(LayoutInflater.from(parent.context))
        return NewsCategoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return Category.CategoryList.size
    }

    override fun onBindViewHolder(holder: NewsCategoryViewHolder, position: Int) {

        val materialButton = holder.itemView.findViewById<MaterialButton>(R.id.category_name_btn)
        val selectedBackgroundColor = materialButton.context.getColor(R.color.white)
        val defaultBackgroundColor = materialButton.context.getColor(R.color.l_blue)

        if(position == selectedItemPosition){
            materialButton.setBackgroundColor(selectedBackgroundColor)
            materialButton.elevation = 0f
        }else{
            materialButton.setBackgroundColor(defaultBackgroundColor)
            materialButton.elevation = 8f
        }

        holder.bind(Category.CategoryList[position])
    }

    private var onItemClickListener : ((String) -> Unit)? = null

    fun setOnItemClickListener(listener : ((String) -> Unit)){
       onItemClickListener = listener
    }

    inner class NewsCategoryViewHolder(private val binding : CategoryListItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(category : String){
            binding.categoryNameBtn.text = category

            binding.categoryNameBtn.setOnClickListener {
                val previousSelected = selectedItemPosition
                selectedItemPosition = adapterPosition
                onItemClickListener?.let {
                    it(category)
                }
                notifyItemChanged(previousSelected)
                notifyItemChanged(selectedItemPosition)
            }
        }
    }

}