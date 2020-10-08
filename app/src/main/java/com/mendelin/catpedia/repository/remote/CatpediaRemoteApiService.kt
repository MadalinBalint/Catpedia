package com.mendelin.catpedia.repository.remote

import android.content.Context
import com.mendelin.catpedia.models.BreedImageResponse
import com.mendelin.catpedia.models.BreedInfoResponse
import com.mendelin.catpedia.models.LoginResponse
import com.mendelin.catpedia.networking.CatpediaApiService
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

    fun mockedUserLogin(context: Context, name: String, password: String): Single<LoginResponse> {
        return try {
            val data = response.getLoginRespone(context, name, password)
            Single.just(data)
        } catch (e: Exception) {
            Single.error(Throwable(e.message))
        }
    }
}