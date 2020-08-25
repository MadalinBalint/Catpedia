package com.mendelin.catpedia.data_access_layer.networking

import androidx.lifecycle.MutableLiveData
import com.mendelin.catpedia.data_access_layer.networking.models.responses.BreedImageResponse
import com.mendelin.catpedia.data_access_layer.networking.models.responses.BreedInfoResponse

class CatpediaRepository {
    companion object {
        private var instance: CatpediaRepository? = null

        fun getInstance(): CatpediaRepository {
            if (instance == null) {
                instance = CatpediaRepository()
            }
            return instance!!
        }
    }

    private val catpediaApi: CatpediaRestApi =
        RetrofitService().createService(CatpediaRestApi::class.java)

    /* Home page */
    fun getListOfCatBreeds(data: MutableLiveData<ArrayList<BreedInfoResponse>>, onError: RetrofitError?) {
        val call = RetrofitCall(data, onError)
        catpediaApi.getListOfCatBreeds().enqueue(call)
    }

    /* Home page products */
    fun getBreedInfo(data: MutableLiveData<BreedImageResponse>, breedId: String, onError: RetrofitError?) {
        val call = RetrofitCall(data, onError)
        catpediaApi.getBreedInfo(breedId).enqueue(call)
    }
}