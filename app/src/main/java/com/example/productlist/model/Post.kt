package com.example.productlist.model

import java.util.*

data class Post(
    val id: String,
    val text: String,
    val image: String,
    val likes: Int,
    val tags: List<String>,
    val publishDate: Date,
    val owner: User
)