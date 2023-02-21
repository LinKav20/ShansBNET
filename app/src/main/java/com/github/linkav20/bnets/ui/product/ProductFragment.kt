package com.github.linkav20.bnets.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.github.linkav20.bnets.R
import com.github.linkav20.bnets.databinding.FragmentProductBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


class ProductFragment : Fragment() {

    private val component by lazy {
        ProductComponent.create()
    }

    private lateinit var binding: FragmentProductBinding
    private val viewModel by viewModels<ProductViewModel> { component.viewModelFactory() }

    private var productId : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actionBar = (activity as AppCompatActivity?)!!.supportActionBar
        actionBar?.title = ""

        loadArgs()
    }

    private fun loadArgs(){
        val id = arguments?.getInt("product_id")
        if (id == null) {
            snackBar(R.string.smth_goes_wrong)
            return
        }
        productId = id
    }

    private fun progressProduct() {
        binding.productContent.visibility = View.GONE
        binding.shimmerLayout.visibility = View.VISIBLE
        binding.shimmerLayout.startShimmer()
    }

    override fun onStart() {
        super.onStart()
        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                snackBarRetry(error)
            }
        }
        loadProduct()
    }

    private fun loadProduct() {
        progressProduct()
        viewModel.loadProduct(productId)

        viewModel.product.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                Glide.with(binding.root)
                    .load(it.image)
                    .centerCrop()
                    .transform(RoundedCorners(10))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.posterImageview)

                binding.titleText.text = it.title
                binding.descText.text = it.desc
                binding.shimmerLayout.stopShimmer()
                binding.shimmerLayout.visibility = View.GONE
                binding.productContent.visibility = View.VISIBLE
            }
        })
    }

    private fun snackBarRetry(message: Int) {
        Snackbar.make(binding.productLayout, message, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.retry) {
                viewModel.loadProduct(productId)
            }.show()
    }

    private fun snackBar(message: Int) {
        Snackbar.make(binding.productLayout, message, Snackbar.LENGTH_LONG).show()
        runBlocking { delay(500L) }
        findNavController().popBackStack()
    }

}