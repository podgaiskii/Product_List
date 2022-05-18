package com.example.productlist.network.dto

data class PostDTO(
    val id: String,
    val text: String,
    val image: String,
    val likes: Int,
    val tags: List<String>,
    val publishDate: String,
    val owner: UserDTO
)