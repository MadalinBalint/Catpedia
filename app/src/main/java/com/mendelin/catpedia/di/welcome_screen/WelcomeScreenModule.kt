package com.mendelin.catpedia.di.welcome_screen

import com.mendelin.catpedia.repositories.LoginMockupRepository
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides

@Module
class WelcomeScreenModule {
    @WelcomeScreenScope
    @Provides
    fun provideLoginMockupRepository(moshi: Moshi): LoginMockupRepository = LoginMockupRepository(moshi)
}