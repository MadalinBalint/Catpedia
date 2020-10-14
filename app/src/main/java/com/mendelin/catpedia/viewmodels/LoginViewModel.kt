package com.mendelin.catpedia.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.mendelin.catpedia.repository.local.LoginRepository
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val repository: LoginRepository
) : ViewModel() {
    fun loginUser(context: Context, name: String, password: String) =
        repository.mockedUserLogin(context, name, password)
}