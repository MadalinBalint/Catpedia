package com.mendelin.catpedia.welcome_screen.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mendelin.catpedia.BuildConfig
import com.mendelin.catpedia.networking.Resource
import com.mendelin.catpedia.models.LoginResponse
import com.mendelin.catpedia.repository.LoginMockupRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val repository: LoginMockupRepository
) : ViewModel() {
    private fun loginUserMocked(context: Context, username: String, password: String): LoginResponse {
        if (username == BuildConfig.MOCKED_USER_NAME && password == BuildConfig.MOCKED_USER_PASSWORD) {
            repository.dataLoginOk.readData(context)?.let {
                return it
            }
        }

        throw Exception(repository.dataLoginWrong.readData(context)?.message)
    }

    fun loginUser(context: Context, name: String, password: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = loginUserMocked(context, name, password)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}