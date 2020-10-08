package com.mendelin.catpedia.di.welcome_screen

import com.mendelin.catpedia.repository.local.JsonStorage
import com.mendelin.catpedia.repository.local.MockedLoginResponse
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
    fun provideMockedLoginResponse(storage: JsonStorage): MockedLoginResponse =
        MockedLoginResponse(storage)
}