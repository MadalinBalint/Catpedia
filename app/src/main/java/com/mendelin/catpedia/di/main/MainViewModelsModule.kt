package com.mendelin.catpedia.di.main

import androidx.lifecycle.ViewModel
import com.mendelin.catpedia.viewmodels.BreedsViewModel
import com.mendelin.catpedia.di.viewmodels.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(BreedsViewModel::class)
    abstract fun bindBreedsViewModel(breedsViewModel: BreedsViewModel): ViewModel
}