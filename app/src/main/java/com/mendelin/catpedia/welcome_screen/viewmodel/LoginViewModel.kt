package com.mendelin.catpedia.welcome_screen.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.mendelin.catpedia.networking.CatpediaApiProvider
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val provider: CatpediaApiProvider
) : ViewModel() {
    fun loginUser(context: Context, name: String, password: String) =
        provider.mockedUserLogin(context, name, password)
}