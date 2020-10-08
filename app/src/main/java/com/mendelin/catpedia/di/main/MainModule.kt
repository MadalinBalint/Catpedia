package com.mendelin.catpedia.di.main

import com.mendelin.catpedia.adapter.BreedsAdapter
import dagger.Module
import dagger.Provides

@Module
class MainModule {
    @MainScope
    @Provides
    fun provideBreedsAdapter(): BreedsAdapter = BreedsAdapter()
}