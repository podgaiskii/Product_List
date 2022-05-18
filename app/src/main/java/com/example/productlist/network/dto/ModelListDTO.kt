package com.example.productlist.network.dto

data class ModelListDTO<DTO>(
    val data: List<DTO>,
    val total: Int,
    val page: Int,
    val limit: Int,
)