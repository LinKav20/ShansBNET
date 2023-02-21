package com.github.linkav20.bnets.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.linkav20.bnets.BuildConfig
import com.github.linkav20.bnets.R
import com.github.linkav20.bnets.models.Product
import com.github.linkav20.bnets.models.ProductImpl
import com.github.linkav20.bnets.models.ProductProgress
import com.github.linkav20.bnets.services.InternetNetworkInformant
import com.github.linkav20.bnets.services.NetworkStatus
import com.github.linkav20.core_network.api.MyApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class CategoryViewModel @Inject constructor(
    private val api: MyApi,
    private val internetNetworkInformant: InternetNetworkInformant
) : ViewModel() {

    private val _state = MutableLiveData(State.NOT_LOADING)
    val state: LiveData<State> = _state

    private val _errorMessage = MutableLiveData<Int?>(null)
    val errorMessage: LiveData<Int?> = _errorMessage

    private val _data = MutableLiveData<List<Product>>()
    val data: LiveData<List<Product>> = _data

    init {
        search("")

        viewModelScope.launch {
            internetNetworkInformant.observe().collectLatest {
                if (it == NetworkStatus.DISCONNECTED) {
                    _errorMessage.postValue(R.string.disconnect_internet)
                } else {
                    _errorMessage.postValue(null)
                    search("")
                }
            }
        }

    }

    private fun getLoaders() = IntRange(1, 6).map { ProductProgress }

    fun search(query: String) {
        _data.postValue(getLoaders())
        viewModelScope.launch {
            _data.postValue(getProducts(query))
        }
    }

    private suspend fun getProducts(query: String): List<Product> {
        val cats = mutableListOf<Product>()
        try {
            _state.postValue(State.LOADING)
            val response = api.getAllProducts(query)
            cats.addAll(response.map {
                ProductImpl(
                    id = it.id,
                    image = "http://shans.d2.i-partner.ru/" + it.image,
                    title = it.name,
                    desc = it.description
                )
            })
            _state.postValue(State.LOAD_SUCCESS)
        } catch (ex: Exception) {
            _state.postValue(State.LOAD_FAILURE)
            _errorMessage.postValue(R.string.loading_failure)
        }
        return cats
    }

}

enum class State {
    NOT_LOADING,
    LOADING,
    LOAD_SUCCESS,
    LOAD_FAILURE;
}