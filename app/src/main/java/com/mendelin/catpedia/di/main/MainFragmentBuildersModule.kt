package com.mendelin.catpedia.di.main

import com.mendelin.catpedia.ui.fragments.BreedInfoFragment
import com.mendelin.catpedia.ui.fragments.BreedsListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeBreedsListFragment(): BreedsListFragment

    @ContributesAndroidInjector
    abstract fun contributeBreedInfoFragment(): BreedInfoFragment
}