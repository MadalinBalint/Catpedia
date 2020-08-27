package com.mendelin.catpedia.presentation_layer.activities.welcome_screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mendelin.catpedia.common.Resource
import com.mendelin.catpedia.presentation_layer.activities.welcome_screen.bussiness_logic.models.LoginResponse
import com.mendelin.catpedia.presentation_layer.activities.welcome_screen.bussiness_logic.repository.LoginMockupRepository
import kotlinx.coroutines.Dispatchers

class LoginViewModel : ViewModel() {
    private fun loginUserMocked(username: String, password: String): LoginResponse {
        if (username == "Pumpkin" && password == "catnip") {
            return LoginMockupRepository.dataLoginOk!!
        } else {
            throw Exception(LoginMockupRepository.dataLoginWrong?.message)
        }
    }

    fun loginUser(name: String, password: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = loginUserMocked(name, password)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}