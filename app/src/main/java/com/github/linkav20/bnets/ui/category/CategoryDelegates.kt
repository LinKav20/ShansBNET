package com.github.linkav20.bnets.ui.category

import android.app.Activity
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.github.linkav20.bnets.databinding.ProductCardBinding
import com.github.linkav20.bnets.databinding.ShimmerProductCardBinding
import com.github.linkav20.bnets.models.Product
import com.github.linkav20.bnets.models.ProductImpl
import com.github.linkav20.bnets.models.ProductProgress
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

object CategoryDelegates {
    fun categoryItemDelegate(fragment: CategoryFragment) =
        adapterDelegateViewBinding<ProductImpl, Product, ProductCardBinding>(
            { inflater, container -> ProductCardBinding.inflate(inflater, container, false) }
        ) {
            bind {
                Glide.with(binding.root)
                    .load(item.image)
                    .centerCrop()
                    .transform(RoundedCorners(8))
                    .transition(withCrossFade())
                    .into(binding.posterImageview)

                binding.titleText.text = item.title

                binding.descText.text = item.desc

                binding.productItem.setOnClickListener {
                    val action =
                        CategoryFragmentDirections.actionCategoryFragmentToProductFragment(item.id)
                    fragment.findNavController().navigate(action)
                }
            }

            onViewRecycled {
                if ((binding.root.context as? Activity)?.isDestroyed?.not() == true) {
                    Glide.with(binding.root).clear(binding.posterImageview)
                }
            }
        }

    fun progressCategoryItemDelegate() =
        adapterDelegateViewBinding<ProductProgress, Product, ShimmerProductCardBinding>(
            { inflater, container -> ShimmerProductCardBinding.inflate(inflater, container, false) }
        ) {
            bind {
                binding.shimmerLayout.startShimmer()
            }
        }
}