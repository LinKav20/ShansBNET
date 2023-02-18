package com.github.linkav20.bnets.ui.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.github.linkav20.bnets.databinding.FragmentCategoryBinding

class CategoryFragment : Fragment() {

    private val component by lazy {
        CategoryComponent.create()
    }

    private lateinit var binding: FragmentCategoryBinding
    private val viewModel by viewModels<CategoryViewModel> { component.viewModelFactory() }

    private val adapter = CategoryAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actionBar = (activity as AppCompatActivity?)!!.supportActionBar
        actionBar?.title = ""

        with(binding) {
            recyclerView.adapter = adapter
            viewModel.data.observe(viewLifecycleOwner, Observer {
                adapter.items = it
            })
        }
    }

}