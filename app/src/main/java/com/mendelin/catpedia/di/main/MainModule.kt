package com.mendelin.catpedia.di.main

import com.mendelin.catpedia.breed_info.repository.BreedInfoRepository
import com.mendelin.catpedia.rest_service.CatpediaApiService
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
    fun provideBreedInfoRepository(): BreedInfoRepository = BreedInfoRepository()
}