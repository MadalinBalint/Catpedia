package com.mendelin.catpedia.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mendelin.catpedia.models.BreedImageResponse
import com.mendelin.catpedia.retrofit.CatpediaApiService
import com.mendelin.catpedia.retrofit.Resource
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class BreedInfoRepository @Inject constructor(
    private val service: CatpediaApiService
) {
    private val breedImage: MutableLiveData<Resource<List<BreedImageResponse>>> = MutableLiveData()

    private fun getBreedImage(breedId: String): LiveData<Resource<List<BreedImageResponse>>> {
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

    fun readData(breedId: String) = getBreedImage(breedId)
}