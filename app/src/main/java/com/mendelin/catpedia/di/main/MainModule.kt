package com.mendelin.catpedia.di.main

import com.mendelin.catpedia.breeds_list.adapter.BreedsAdapter
import com.mendelin.catpedia.networking.CatpediaApiProvider
import com.mendelin.catpedia.networking.CatpediaApiService
import com.mendelin.catpedia.repository.BreedInfoRepository
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
    fun provideRestApiProvider(service: CatpediaApiService): CatpediaApiProvider =
        CatpediaApiProvider(service)

    @MainScope
    @Provides
    fun provideBreedInfoRepository(apiProvider: CatpediaApiProvider): BreedInfoRepository =
        BreedInfoRepository(apiProvider)

    @MainScope
    @Provides
    fun provideCatBreedsRepository(apiProvider: CatpediaApiProvider): CatBreedsRepository =
        CatBreedsRepository(apiProvider)

    @MainScope
    @Provides
    fun provideBreedsAdapter(): BreedsAdapter = BreedsAdapter()
}