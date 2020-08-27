package com.mendelin.catpedia.presentation_layer.fragments.breeds_list.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mendelin.catpedia.common.Resource
import com.mendelin.catpedia.data_access_layer.networking.CatpediaApi
import com.mendelin.catpedia.data_access_layer.networking.models.BreedInfoResponse
import kotlinx.coroutines.Dispatchers

class BreedsViewModel : ViewModel() {
    val breedsList = MutableLiveData<ArrayList<BreedInfoResponse>>()
    val query = MutableLiveData<String>()

    fun getBreedsList() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = CatpediaApi.service.getListOfCatBreeds()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getBreedImage(breedId: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = CatpediaApi.service.getBreedImage(breedId)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}