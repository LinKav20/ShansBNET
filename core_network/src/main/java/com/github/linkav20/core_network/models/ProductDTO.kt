package com.github.linkav20.core_network.models

import com.google.gson.annotations.SerializedName

data class ProductDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("image") val image: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
)