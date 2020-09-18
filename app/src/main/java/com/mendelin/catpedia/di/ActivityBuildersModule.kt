package com.mendelin.catpedia.di

import com.mendelin.catpedia.di.main.MainFragmentBuildersModule
import com.mendelin.catpedia.di.main.MainModule
import com.mendelin.catpedia.di.main.MainScope
import com.mendelin.catpedia.di.main.MainViewModelsModule
import com.mendelin.catpedia.di.welcome_screen.WelcomeScreenScope
import com.mendelin.catpedia.di.welcome_screen.WelcomeScreenViewModelsModule
import com.mendelin.catpedia.main_screen.MainActivity
import com.mendelin.catpedia.welcome_screen.ui.WelcomeScreenActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/* Only activities */

@Module
abstract class ActivityBuildersModule {
    @WelcomeScreenScope
    @ContributesAndroidInjector(
        modules = [
            WelcomeScreenViewModelsModule::class
        ]
    )
    abstract fun contributeWelcomeScreenActivity(): WelcomeScreenActivity

    @MainScope
    @ContributesAndroidInjector(
        modules = [
            MainFragmentBuildersModule::class,
            MainViewModelsModule::class,
            MainModule::class
        ]
    )
    abstract fun contributeMainActivity(): MainActivity
}