package com.github.linkav20.core_network.api

import com.github.linkav20.core_network.models.ProductDTO
import retrofit2.http.GET

interface MyApi {

    @GET("/api/ppp/item/{id}")
    suspend fun getProduct() : ProductDTO

    @GET("/api/v2.2/index")
    suspend fun getAllProducts(): List<ProductDTO>
}