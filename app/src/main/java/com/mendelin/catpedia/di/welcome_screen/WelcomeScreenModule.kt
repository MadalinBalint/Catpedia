package com.mendelin.catpedia.di.welcome_screen

import com.google.gson.Gson
import com.mendelin.catpedia.repository.local.MockedLoginResponse
import com.mendelin.catpedia.repository.storage.JsonStorage
import dagger.Module
import dagger.Provides

@Module
class WelcomeScreenModule {
    @WelcomeScreenScope
    @Provides
    fun provideJsonStorage(gson: Gson): JsonStorage = JsonStorage(gson)

    @WelcomeScreenScope
    @Provides
    fun provideMockedLoginResponse(storage: JsonStorage): MockedLoginResponse =
        MockedLoginResponse(storage)
}