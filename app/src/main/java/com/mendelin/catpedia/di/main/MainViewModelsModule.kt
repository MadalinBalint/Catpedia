package com.mendelin.catpedia.di.main

import androidx.lifecycle.ViewModel
import com.mendelin.catpedia.breeds_list.viewmodel.BreedsViewModel
import com.mendelin.catpedia.di.viewmodels.ViewModelKey
import com.mendelin.catpedia.welcome_screen.viewmodel.LoginViewModel
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