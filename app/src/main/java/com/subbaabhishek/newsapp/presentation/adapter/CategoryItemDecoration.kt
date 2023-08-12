package com.subbaabhishek.newsapp.presentation.adapter

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView

class CategoryItemDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
        outRect.right = 8
        outRect.left = 8
    }
}