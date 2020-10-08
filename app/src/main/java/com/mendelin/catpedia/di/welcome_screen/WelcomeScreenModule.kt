package com.mendelin.catpedia.di.welcome_screen

import com.mendelin.catpedia.repository.local.JsonStorage
import com.mendelin.catpedia.repository.CatpediaApiMockedProvider
import com.mendelin.catpedia.repository.MockedLoginRepository
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides

@Module
class WelcomeScreenModule {
    @WelcomeScreenScope
    @Provides
    fun provideJsonStorage(moshi: Moshi): JsonStorage = JsonStorage(moshi)

    @WelcomeScreenScope
    @Provides
    fun provideLoginMockupRepository(storage: JsonStorage): MockedLoginRepository =
        MockedLoginRepository(storage)

    @WelcomeScreenScope
    @Provides
    fun provideRestApiMockedProvider(repository: MockedLoginRepository): CatpediaApiMockedProvider =
        CatpediaApiMockedProvider(repository)
}