package com.github.linkav20.bnets.models

data class ProductImpl(
    override val id: Int,
    val title: String,
    val desc: String,
    val image: String
) : Product {

}
