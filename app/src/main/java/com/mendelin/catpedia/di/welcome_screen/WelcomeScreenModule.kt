package com.mendelin.catpedia.di.welcome_screen

import com.mendelin.catpedia.repository.MockedRepository
import com.mendelin.catpedia.repository.local.JsonStorage
import com.mendelin.catpedia.repository.local.MockedLoginResponse
import com.mendelin.catpedia.repository.remote.CatpediaRemoteApiServiceMocked
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

    @WelcomeScreenScope
    @Provides
    fun provideCatpediaRemoteApiServiceMocked(response: MockedLoginResponse): CatpediaRemoteApiServiceMocked =
        CatpediaRemoteApiServiceMocked(response)

    @WelcomeScreenScope
    @Provides
    fun provideMockedRepository(remoteServiceMocked: CatpediaRemoteApiServiceMocked): MockedRepository =
        MockedRepository(remoteServiceMocked)
}