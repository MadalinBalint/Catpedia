package com.mendelin.catpedia.di.main

import com.mendelin.catpedia.repositories.BreedInfoRepository
import com.mendelin.catpedia.breeds_list.adapter.BreedsAdapter
import com.mendelin.catpedia.repositories.CatBreedsRepository
import com.mendelin.catpedia.retrofit.CatpediaApiNetworkCall
import com.mendelin.catpedia.retrofit.CatpediaApiService
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
    fun provideRestApiNetworkCall(service: CatpediaApiService): CatpediaApiNetworkCall = CatpediaApiNetworkCall(service)

    @MainScope
    @Provides
    fun provideBreedInfoRepository(apiNetworkCall: CatpediaApiNetworkCall): BreedInfoRepository = BreedInfoRepository(apiNetworkCall)

    @MainScope
    @Provides
    fun provideCatBreedsRepository(apiNetworkCall: CatpediaApiNetworkCall): CatBreedsRepository = CatBreedsRepository(apiNetworkCall)

    @MainScope
    @Provides
    fun provideBreedsAdapter(): BreedsAdapter = BreedsAdapter()
}