package com.github.linkav20.core_network.api

import com.github.linkav20.core_network.models.ProductDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface MyApi {

    @GET("/api/ppp/item")
    suspend fun getProduct(@Query("id") id: Int) : ProductDTO

    @GET("/api/ppp/index")
    suspend fun getAllProducts(): List<ProductDTO>
}