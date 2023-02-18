package com.github.linkav20.bnets.ui.category

import androidx.recyclerview.widget.DiffUtil
import com.github.linkav20.bnets.models.Product
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class CategoryAdapter(
    fragment: CategoryFragment
) : AsyncListDifferDelegationAdapter<Product>(DIFF_CALLBACK) {

    init {
        delegatesManager.addDelegate(CategoryDelegates.categoryItemDelegate(fragment))
            .addDelegate(CategoryDelegates.progressCategoryItemDelegate())
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Product,
                newItem: Product
            ): Boolean =
                oldItem.equals(newItem)
        }
    }
}