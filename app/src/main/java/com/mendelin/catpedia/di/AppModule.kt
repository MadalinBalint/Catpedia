package com.mendelin.catpedia.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mendelin.catpedia.BuildConfig
import com.mendelin.catpedia.networking.CatpediaApiService
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Singleton

@Module
class AppModule {
    companion object {
        const val TRY_COUNT = 3
        const val TRY_PAUSE_BETWEEN = 1000L
    }

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

                    var response = chain.proceed(builder.build())

                    /* Automatically retry the call for N times if you receive a server error (5xx) */
                    var tryCount = 0
                    while (response.code() in 500..599 && tryCount < TRY_COUNT) {
                        try {
                            response.close()
                            Thread.sleep(TRY_PAUSE_BETWEEN)
                            response = chain.proceed(builder.build())
                            Timber.e("Request is not successful - $tryCount")
                        } catch (e: Exception) {
                            Timber.e(e.localizedMessage)
                        } finally {
                            tryCount++
                        }
                    }

                    /* Intercept empty body response */
                    if (response.body()?.contentLength() == 0L) {
                        val contentType: MediaType? = response.body()!!.contentType()
                        val body: ResponseBody = ResponseBody.create(contentType, "{}")
                        return@Interceptor response.newBuilder().body(body).build()
                    }

                    return@Interceptor response
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