package com.mendelin.catpedia.di.main

import com.mendelin.catpedia.repository.BreedInfoRepository
import com.mendelin.catpedia.breeds_list.adapter.BreedsAdapter
import com.mendelin.catpedia.local_data_source.JsonStorage
import com.mendelin.catpedia.repository.CatBreedsRepository
import com.mendelin.catpedia.networking.CatpediaApiNetworkCall
import com.mendelin.catpedia.networking.CatpediaApiService
import com.mendelin.catpedia.repository.MockedLoginRepository
import com.squareup.moshi.Moshi
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