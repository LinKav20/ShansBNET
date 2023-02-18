package com.github.linkav20.bnets.ui.product

import androidx.lifecycle.ViewModel
import com.github.linkav20.core_network.api.MyApi
import javax.inject.Inject

class ProductViewModel @Inject constructor(
    private val api: MyApi
): ViewModel() {
}