package com.mendelin.catpedia.di.main

import com.mendelin.catpedia.adapter.breeds_list.BreedsAdapter
import com.mendelin.catpedia.networking.CatpediaApiService
import com.mendelin.catpedia.repository.CatBreedsRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class MainModule {
    @MainScope
    @Provides
    fun provideRestApi(retrofit: Retrofit): CatpediaApiService =
        retrofit.create(CatpediaApiService::class.java)

    @MainScope
    @Provides
    fun provideCatBreedsRepository(apiService: CatpediaApiService): CatBreedsRepository =
        CatBreedsRepository(apiService)

    @MainScope
    @Provides
    fun provideBreedsAdapter(): BreedsAdapter = BreedsAdapter()
}