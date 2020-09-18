package com.mendelin.catpedia.di.main

import com.mendelin.catpedia.breed_info.ui.BreedInfoFragment
import com.mendelin.catpedia.breeds_list.ui.BreedsListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeBreedsListFragment(): BreedsListFragment

    @ContributesAndroidInjector
    abstract fun contributeBreedInfoFragment(): BreedInfoFragment
}