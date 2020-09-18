package com.mendelin.catpedia.breed_info.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.mendelin.catpedia.breed_info.models.BreedImageResponse
import com.mendelin.catpedia.rest_service.CatpediaApiService
import com.mendelin.catpedia.retrofit.Resource
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class BreedInfoRepository @Inject constructor(
    private val service: CatpediaApiService
) : LiveData<Resource<List<BreedImageResponse>>>() {
    private fun getBreedImage(breedId: String): LiveData<Resource<List<BreedImageResponse>>> {
        return LiveDataReactiveStreams.fromPublisher(
            service.getBreedImage(breedId)
                .doOnError {
                    Resource.error(data = null, message = it.message ?: "Error Occurred!")
                }
                .map<Resource<List<BreedImageResponse>>?> {
                    Resource.success(it)
                }
                .subscribeOn(Schedulers.io())
        )
    }

    fun readData(breedId: String): LiveData<Resource<List<BreedImageResponse>>> =
        getBreedImage(breedId)
}