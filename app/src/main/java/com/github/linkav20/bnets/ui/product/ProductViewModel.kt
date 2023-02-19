package com.github.linkav20.bnets.ui.product

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.linkav20.bnets.models.ProductImpl
import com.github.linkav20.core_network.api.MyApi
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductViewModel @Inject constructor(
    private val api: MyApi
) : ViewModel() {

    private val _product = MutableLiveData<ProductImpl>()
    val product: LiveData<ProductImpl> = _product

    fun loadProduct(id: Int) {
        viewModelScope.launch {
            val value = getProduct(id)
            _product.postValue(value)
        }
    }

    private suspend fun getProduct(id: Int): ProductImpl {
        try {
            val response = api.getProduct(id)

            return ProductImpl(
                id = response.id,
                image = "http://shans.d2.i-partner.ru" + response.image,
                title = response.name,
                desc = response.description
            )
        } catch (e: Exception) {

        }
    }
}