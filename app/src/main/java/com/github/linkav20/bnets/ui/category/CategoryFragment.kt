package com.github.linkav20.bnets.ui.category

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import com.github.linkav20.bnets.R
import com.github.linkav20.bnets.databinding.FragmentCategoryBinding
import com.github.linkav20.bnets.utils.onQueryTextChanged
import com.google.android.material.snackbar.Snackbar

class CategoryFragment : Fragment() {

    private val component by lazy {
        CategoryComponent.create()
    }

    private lateinit var binding: FragmentCategoryBinding
    private val viewModel by viewModels<CategoryViewModel> { component.viewModelFactory() }

    private val adapter = CategoryAdapter(this)

    private var searchQuery = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setMenu()

        val actionBar = (activity as AppCompatActivity?)!!.supportActionBar
        actionBar?.title = ""

        with(binding) {
            recyclerView.adapter = adapter
            viewModel.data.observe(viewLifecycleOwner) {
                adapter.items = it
            }
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                snackBarRetry(error)
            }
        }
    }

    private fun setMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
                activity?.menuInflater?.inflate(R.menu.menu, menu)

                val searchItem = menu.findItem(R.id.action_search)
                val searchView = searchItem.actionView as SearchView

                searchView.onQueryTextChanged {
                    searchQuery = it
                    viewModel.search(it)
                }

                searchView.setOnQueryTextFocusChangeListener { _, newFocus ->
                    if (!newFocus) {
                        searchItem.collapseActionView();
                        searchQuery = ""
                        viewModel.search("")
                    }
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }
        }, viewLifecycleOwner)
    }

    private fun snackBarRetry(message: Int) {
        Snackbar.make(binding.categoriesLayout, message, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.retry) {
                viewModel.search(searchQuery)
            }.show()
    }
}