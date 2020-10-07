package com.mendelin.catpedia.di.welcome_screen

import androidx.lifecycle.ViewModel
import com.mendelin.catpedia.di.viewmodels.ViewModelKey
import com.mendelin.catpedia.viewmodels.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class WelcomeScreenViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel
}