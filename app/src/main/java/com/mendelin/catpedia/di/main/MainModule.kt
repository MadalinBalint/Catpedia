package com.mendelin.catpedia.di.main

import com.mendelin.catpedia.adapter.BreedsAdapter
import com.mendelin.catpedia.networking.CatpediaApiService
import com.mendelin.catpedia.repository.CatBreedsRepository
import com.mendelin.catpedia.repository.local.JsonStorage
import com.mendelin.catpedia.repository.local.MockedLoginResponse
import com.mendelin.catpedia.repository.remote.CatpediaRemoteApiService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class MainModule {
    @MainScope
    @Provides
    fun provideApiService(retrofit: Retrofit): CatpediaApiService =
        retrofit.create(CatpediaApiService::class.java)

    @MainScope
    @Provides
    fun provideCatBreedsRepository(remoteService: CatpediaRemoteApiService): CatBreedsRepository =
        CatBreedsRepository(remoteService)

    @MainScope
    @Provides
    fun provideBreedsAdapter(): BreedsAdapter = BreedsAdapter()
}