package com.mendelin.catpedia.breed_info.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.mendelin.catpedia.breed_info.models.BreedImageResponse
import com.mendelin.catpedia.rest_service.CatpediaApi
import com.mendelin.catpedia.retrofit.Resource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class BreedInfoRepository @Inject constructor() : LiveData<Resource<List<BreedImageResponse>>>() {
    private fun getBreedImage(breedId: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = CatpediaApi.service.getBreedImage(breedId)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun readData(breedId: String): LiveData<Resource<List<BreedImageResponse>>> =
        getBreedImage(breedId)
}