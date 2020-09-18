package com.mendelin.catpedia.breeds_list.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.mendelin.catpedia.breeds_list.models.BreedInfoResponse
import com.mendelin.catpedia.rest_service.CatpediaApiService
import com.mendelin.catpedia.retrofit.Resource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class CatBreedsRepository @Inject constructor(
    val service: CatpediaApiService
) : LiveData<Resource<List<BreedInfoResponse>>>() {
    private fun getBreedsList() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = service.getListOfCatBreeds()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun readData(): LiveData<Resource<List<BreedInfoResponse>>> = getBreedsList()
}