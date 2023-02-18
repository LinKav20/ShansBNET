package com.github.linkav20.bnets.models

data class CategoryImpl(
    override val id: Int,
    val title: String,
    val desc: String,
    val image: String
) : Category {

}
