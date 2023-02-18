package com.github.linkav20.bnets.ui.product

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.github.linkav20.bnets.databinding.FragmentProductBinding


class ProductFragment : Fragment() {

    private val component by lazy {
        ProductComponent.create()
    }

    private lateinit var binding: FragmentProductBinding
    private val viewModel by viewModels<ProductViewModel> { component.viewModelFactory() }

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

        val id = arguments?.getInt("product_id")
        if (id == null) {
            toastError()
        } else {
            init(id)
        }
    }

    private fun init(id: Int) {
        viewModel.loadProduct(id)
        viewModel.product.observe(viewLifecycleOwner, Observer {
            Glide.with(binding.root)
                .load(it.image)
                .centerCrop()
                .transform(RoundedCorners(10))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.posterImageview)

            binding.titleText.text = it.title
            binding.descText.text = it.desc
        })
    }

    private fun toastError() {
        Toast.makeText(
            context,
            "Something goes wrong",
            Toast.LENGTH_LONG
        ).show()
        findNavController().popBackStack()
    }

}