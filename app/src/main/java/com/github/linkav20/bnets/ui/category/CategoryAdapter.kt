package com.github.linkav20.bnets.ui.category

import androidx.recyclerview.widget.DiffUtil
import com.github.linkav20.bnets.models.Category
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class CategoryAdapter(
    fragment: CategoryFragment
) : AsyncListDifferDelegationAdapter<Category>(DIFF_CALLBACK) {

    init {
        delegatesManager.addDelegate(CategoryDelegates.categoryItemDelegate(fragment))
            .addDelegate(CategoryDelegates.progressCategoryItemDelegate())
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Category>() {
            override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Category,
                newItem: Category
            ): Boolean =
                oldItem.equals(newItem)
        }
    }
}