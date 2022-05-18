package com.example.productlist.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://dummyapi.io/"
private const val API_TOKEN = "6284bf2f3d04c2a63ffb3b38"

object Network {
    @Volatile
    private var RETROFIT_INSTANCE: Retrofit? = null

    private fun getRetrofit(): Retrofit {
        synchronized(this) {
            if (RETROFIT_INSTANCE == null) {
                val headerInterceptor = Interceptor { chain ->
                    val newRequest: Request =
                        chain.request().newBuilder().addHeader("app-id", API_TOKEN)
                            .build()
                    chain.proceed(newRequest)
                }

                val loggingInterceptor = HttpLoggingInterceptor().also {
                    it.level = HttpLoggingInterceptor.Level.BODY
                }

                val client: OkHttpClient = OkHttpClient.Builder().apply {
                    interceptors().add(headerInterceptor)
                    interceptors().add(loggingInterceptor)
                }.build()

                RETROFIT_INSTANCE = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
            }
            return RETROFIT_INSTANCE!!
        }
    }

    private var DUMMY_API_INSTANCE: DummyApi? = null

    val dummyApi: DummyApi
        get() {
            if (DUMMY_API_INSTANCE == null) {
                DUMMY_API_INSTANCE = getRetrofit().create(DummyApi::class.java)
            }
            return DUMMY_API_INSTANCE!!
        }
}