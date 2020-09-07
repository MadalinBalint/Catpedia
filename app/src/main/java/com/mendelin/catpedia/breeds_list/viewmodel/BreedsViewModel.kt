package com.mendelin.catpedia.breeds_list.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mendelin.catpedia.retrofit.Resource
import com.mendelin.catpedia.rest_service.CatpediaApi
import com.mendelin.catpedia.breeds_list.models.BreedInfoResponse
import kotlinx.coroutines.Dispatchers

class BreedsViewModel : ViewModel() {
    private val originalBreedList: ArrayList<BreedInfoResponse> = arrayListOf()

    val breedsList = MutableLiveData<ArrayList<BreedInfoResponse>>()

    val errorFilter = MutableLiveData<String>()

    fun getOriginalBreedList(): ArrayList<BreedInfoResponse> = originalBreedList
    fun setOriginalBreedList(list: List<BreedInfoResponse>) {
        originalBreedList.apply {
            clear()
            addAll(list)
        }

        breedsList.value = originalBreedList
    }

    fun filter(query: String?) {
        query?.let {
            if (query.isNotEmpty()) {
                val filteredList = originalBreedList.filter { it.origin?.toLowerCase() == query.toLowerCase() ||
                        it.origin?.toLowerCase()?.indexOf(query.toLowerCase())!! >= 0 }

                if (filteredList.isEmpty()) {
                    errorFilter.value = "The country '${query}' for the cat's origin doesn't exist in our list."
                } else
                    breedsList.value = ArrayList(filteredList)
            } else
                breedsList.value = originalBreedList
        }
    }

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