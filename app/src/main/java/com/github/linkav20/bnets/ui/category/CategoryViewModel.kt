package com.github.linkav20.bnets.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.linkav20.bnets.models.Category
import com.github.linkav20.bnets.models.CategoryProgress
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel() {

    private val _data = MutableLiveData<List<Category>>()
    val data: LiveData<List<Category>> = _data


    init {
        viewModelScope.launch {
            _data.postValue(getLoaders())
            //val items = getCategories()
            //_data.postValue(items)
        }
    }

    private fun getLoaders()= IntRange(1, 6).map { CategoryProgress }

    private suspend fun getCategories() : List<Category>{
        val cats = mutableListOf<Category>()
        return cats
    }
}