package com.github.linkav20.bnets.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.linkav20.bnets.models.Category
import com.github.linkav20.bnets.models.CategoryImpl
import com.github.linkav20.bnets.models.CategoryProgress
import com.github.linkav20.core_network.api.MyApi
import kotlinx.coroutines.launch
import javax.inject.Inject

class CategoryViewModel @Inject constructor(
    private val api: MyApi
)  : ViewModel() {

    private val _data = MutableLiveData<List<Category>>()
    val data: LiveData<List<Category>> = _data


    init {
        viewModelScope.launch {
            _data.postValue(getLoaders())
            val items = getCategories()
            _data.postValue(items)
        }
    }

    private fun getLoaders()= IntRange(1, 6).map { CategoryProgress }

    private suspend fun getCategories() : List<Category>{
        val cats = mutableListOf<Category>()
        cats.add(CategoryImpl(1,"lol","lol","lol"))
        return cats
    }
}