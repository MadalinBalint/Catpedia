package com.mendelin.catpedia.retrofit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mendelin.catpedia.models.BreedImageResponse
import com.mendelin.catpedia.models.BreedInfoResponse
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class CatpediaApiNetworkCall @Inject constructor(
    private val service: CatpediaApiService
) {
    fun getBreedsList(): LiveData<Resource<List<BreedInfoResponse>>> {
        val breedsList: MutableLiveData<Resource<List<BreedInfoResponse>>> = MutableLiveData()
        breedsList.postValue(Resource.loading(data = null))

        service.getListOfCatBreeds()
            .subscribeOn(Schedulers.io())
            .subscribe({
                breedsList.postValue(Resource.success(it))
            },
                {
                    breedsList.postValue(Resource.error(data = null, message = it.message
                        ?: "Error Occurred!"))
                }
            )

        return breedsList
    }

    fun getBreedImage(breedId: String): LiveData<Resource<List<BreedImageResponse>>> {
        val breedImage: MutableLiveData<Resource<List<BreedImageResponse>>> = MutableLiveData()
        breedImage.postValue(Resource.loading(data = null))

        service.getBreedImage(breedId)
            .subscribeOn(Schedulers.io())
            .subscribe({
                breedImage.postValue(Resource.success(it))
            },
                {
                    breedImage.postValue(Resource.error(data = null, message = it.message
                        ?: "Error Occurred!"))
                }
            )

        return breedImage
    }
}