package com.mendelin.catpedia.breeds_list.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.mendelin.catpedia.breeds_list.models.BreedInfoResponse
import com.mendelin.catpedia.retrofit.CatpediaApiService
import com.mendelin.catpedia.retrofit.Resource
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class CatBreedsRepository @Inject constructor(
    private val service: CatpediaApiService
) : LiveData<Resource<List<BreedInfoResponse>>>() {
    private fun getBreedsList(): LiveData<Resource<List<BreedInfoResponse>>> {
        return LiveDataReactiveStreams.fromPublisher(
            service.getListOfCatBreeds()
                .doOnError {
                    Resource.error(data = null, message = it.message ?: "Error Occurred!")
                }
                .map<Resource<List<BreedInfoResponse>>?> {
                    Resource.success(it)
                }
                .subscribeOn(Schedulers.io())
        )
    }

    fun readData(): LiveData<Resource<List<BreedInfoResponse>>> = getBreedsList()
}