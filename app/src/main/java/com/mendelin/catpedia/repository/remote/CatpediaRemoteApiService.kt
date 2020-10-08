package com.mendelin.catpedia.repository.remote

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mendelin.catpedia.models.BreedImageResponse
import com.mendelin.catpedia.models.BreedInfoResponse
import com.mendelin.catpedia.models.LoginResponse
import com.mendelin.catpedia.networking.CatpediaApiService
import com.mendelin.catpedia.networking.Resource
import com.mendelin.catpedia.repository.local.MockedLoginResponse
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class CatpediaRemoteApiService @Inject constructor(
    private val service: CatpediaApiService,
    private val response: MockedLoginResponse
) {
    fun getCatBreeds(): Single<List<BreedInfoResponse>> {
        return service.getListOfCatBreeds()
            .subscribeOn(Schedulers.io())
            .flatMapIterable { it }
            .flatMapSingle { breed ->
                getBreedImage(breed.id)
                    .map { imageList -> breed.image = imageList?.firstOrNull() }
                    .flatMap { Single.just(breed) }
            }
            .toList()
    }

    private fun getBreedImage(breedId: String): Single<List<BreedImageResponse>> =
        service.getBreedImage(breedId)

    fun mockedUserLogin(
        context: Context,
        name: String,
        password: String
    ): LiveData<Resource<LoginResponse>> {
        val loginResponse: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()

        loginResponse.postValue(Resource.loading(data = null))

        try {
            val data = response.getLoginRespone(context, name, password)
            loginResponse.postValue(Resource.success(data))
        } catch (exception: Exception) {
            loginResponse.postValue(
                Resource.error(
                    data = null,
                    message = exception.message ?: "Error Occurred!"
                )
            )
        }

        return loginResponse
    }
}