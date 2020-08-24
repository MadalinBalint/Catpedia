package com.mendelin.catpedia.data_access_layer.networking

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {

    companion object {

        private val okHttpClient = OkHttpClient.Builder().apply {
            addInterceptor(
                Interceptor { chain ->
                    val builder = chain.request().newBuilder()
                    builder.header("x-api-key", "af33a72b-4420-4190-a803-cab3b273c3db")
                    return@Interceptor chain.proceed(builder.build())
                }
            )
        }.build()

        private val retrofit = Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    fun <S> createService(serviceClass: Class<S>): S = retrofit.create(serviceClass)
}