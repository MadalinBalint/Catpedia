package com.mendelin.catpedia.di.welcome_screen

import com.mendelin.catpedia.welcome_screen.bussiness_logic.repository.LoginMockupRepository
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides

@Module
class WelcomeScreenModule {
    @WelcomeScreenScope
    @Provides
    fun provideLoginMockupRepository(moshi: Moshi): LoginMockupRepository = LoginMockupRepository(moshi)
}