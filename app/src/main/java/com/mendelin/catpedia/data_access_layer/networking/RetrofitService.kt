package com.mendelin.catpedia.data_access_layer.networking

import com.mendelin.catpedia.data_access_layer.server.ServerConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitService {

    companion object {
        val moshi: Moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        private val okHttpClient = OkHttpClient.Builder().apply {
            addInterceptor(
                Interceptor { chain ->
                    val builder = chain.request().newBuilder()
                    builder.header(ServerConfig.getApiHeader(), ServerConfig.getApiKey())
                    return@Interceptor chain.proceed(builder.build())
                }
            )
        }.build()

        private val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(ServerConfig.getBaseUrl())
            .client(okHttpClient)
            .build()
    }

    fun <S> createService(serviceClass: Class<S>): S = retrofit.create(serviceClass)
}