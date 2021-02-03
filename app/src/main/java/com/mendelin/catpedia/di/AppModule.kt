package com.mendelin.catpedia.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mendelin.catpedia.BuildConfig
import com.mendelin.catpedia.networking.CatpediaApiService
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {
    @Singleton
    @Provides
    fun provideGsonInstance(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    @Singleton
    @Provides
    fun provideOkHttpClientInstance(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(
                Interceptor { chain ->
                    val builder = chain.request().newBuilder()
                    builder.header(BuildConfig.API_HEADER, BuildConfig.API_KEY)
                    return@Interceptor chain.proceed(builder.build())
                }
            )
        }.build()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilderInstance(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): CatpediaApiService =
        retrofit.create(CatpediaApiService::class.java)
}