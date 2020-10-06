package com.mendelin.catpedia.networking

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mendelin.catpedia.models.BreedImageResponse
import com.mendelin.catpedia.models.BreedInfoResponse
import com.mendelin.catpedia.models.LoginResponse
import com.mendelin.catpedia.repository.MockedLoginRepository
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class CatpediaApiMockedProvider @Inject constructor(
    private val mockedLoginRepository: MockedLoginRepository
) {
    fun mockedUserLogin(context: Context, name: String, password: String): LiveData<Resource<LoginResponse>> {
        val loginResponse: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()

        loginResponse.postValue(Resource.loading(data = null))

        try {
            val data = mockedLoginRepository.getLoginRespone(context, name, password)
            loginResponse.postValue(Resource.success(data))
        } catch (exception: Exception) {
            loginResponse.postValue(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }

        return loginResponse
    }
}