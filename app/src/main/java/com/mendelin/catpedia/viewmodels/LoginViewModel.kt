package com.mendelin.catpedia.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.mendelin.catpedia.repository.CatBreedsRepository
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val repository: CatBreedsRepository
) : ViewModel() {
    fun loginUser(context: Context, name: String, password: String) =
        repository.mockedUserLogin(context, name, password)
}