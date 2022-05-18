package com.example.productlist.network

import com.example.productlist.network.dto.ModelListDTO
import com.example.productlist.network.dto.PostDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface DummyApi {

    @GET("/data/v1/post")
    suspend fun getPosts(
        @Query("page") page: Int = 0,
        @Query("limit") pageSize: Int = 50,
    ): ModelListDTO<PostDTO>
}