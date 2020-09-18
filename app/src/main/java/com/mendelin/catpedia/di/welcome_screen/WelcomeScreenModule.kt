package com.mendelin.catpedia.di.welcome_screen

import com.mendelin.catpedia.welcome_screen.bussiness_logic.repository.LoginMockupRepository
import dagger.Module
import dagger.Provides

@Module
class WelcomeScreenModule {
    @WelcomeScreenScope
    @Provides
    fun provideLoginMockupRepository(): LoginMockupRepository = LoginMockupRepository()
}