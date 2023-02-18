package com.github.linkav20.bnets.ui.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.github.linkav20.bnets.R
import com.github.linkav20.bnets.databinding.FragmentCategoryBinding

class CategoryFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding
    private val viewModel: CategoryViewModel by viewModels()

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

        with(binding) {
            recyclerView.adapter = adapter
            viewModel.data.observe(viewLifecycleOwner, Observer {
                adapter.items = it
            })
        }
    }

}