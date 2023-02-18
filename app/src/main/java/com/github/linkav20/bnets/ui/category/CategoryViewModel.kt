package com.github.linkav20.bnets.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.linkav20.bnets.models.Product
import com.github.linkav20.bnets.models.ProductImpl
import com.github.linkav20.bnets.models.ProductProgress
import com.github.linkav20.core_network.api.MyApi
import kotlinx.coroutines.launch
import javax.inject.Inject

class CategoryViewModel @Inject constructor(
    private val api: MyApi
) : ViewModel() {

    private val _data = MutableLiveData<List<Product>>()
    val data: LiveData<List<Product>> = _data


    init {
        viewModelScope.launch {
            _data.postValue(getLoaders())
            val items = getCategories()
            _data.postValue(items)
        }
    }

    private fun getLoaders() = IntRange(1, 6).map { ProductProgress }

    private suspend fun getCategories(): List<Product> {
        val cats = mutableListOf<Product>()
        val response = api.getAllProducts()
        cats.addAll(response.map {
            ProductImpl(
                id = it.id,
                image = "http://shans.d2.i-partner.ru" + it.image,
                title = it.name,
                desc = it.description
            )
        })
        return cats
    }
}