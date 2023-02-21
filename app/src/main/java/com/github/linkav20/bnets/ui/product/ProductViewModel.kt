package com.github.linkav20.bnets.ui.product

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.linkav20.bnets.BuildConfig
import com.github.linkav20.bnets.R
import com.github.linkav20.bnets.models.ProductImpl
import com.github.linkav20.bnets.services.InternetNetworkInformant
import com.github.linkav20.bnets.services.NetworkStatus
import com.github.linkav20.core_network.api.MyApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductViewModel @Inject constructor(
    private val api: MyApi,
    private val internetNetworkInformant: InternetNetworkInformant
) : ViewModel() {

    private val _product = MutableLiveData<ProductImpl?>()
    val product: LiveData<ProductImpl?> = _product

    private val _errorMessage = MutableLiveData<Int?>(null)
    val errorMessage: LiveData<Int?> = _errorMessage

    init {
        viewModelScope.launch {
            internetNetworkInformant.observe().collectLatest {
                if (it == NetworkStatus.DISCONNECTED) {
                    _errorMessage.postValue(R.string.disconnect_internet)
                } else {
                    _errorMessage.postValue(null)
                }
            }
        }
    }

    fun loadProduct(id: Int) {
        viewModelScope.launch {
            val value = getProduct(id)
            _product.postValue(value)
        }
    }

    private suspend fun getProduct(id: Int): ProductImpl? {
        try {
            val response = api.getProduct(id)

            return ProductImpl(
                id = response.id,
                image = "http://shans.d2.i-partner.ru/" + response.image,
                title = response.name,
                desc = response.description
            )
        } catch (_: Exception) {
            _errorMessage.postValue(R.string.cannot_load_product)
        }

        return null
    }
}