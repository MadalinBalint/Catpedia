package com.mendelin.catpedia.welcome_screen.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mendelin.catpedia.networking.Resource
import com.mendelin.catpedia.repository.MockedLoginRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val repository: MockedLoginRepository
) : ViewModel() {
    fun loginUser(context: Context, name: String, password: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getLoginRespone(context, name, password)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}