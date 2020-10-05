package com.mendelin.catpedia.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mendelin.catpedia.models.BreedInfoResponse
import com.mendelin.catpedia.retrofit.CatpediaApiService
import com.mendelin.catpedia.retrofit.Resource
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class CatBreedsRepository @Inject constructor(
    private val service: CatpediaApiService
) {
    private val breedsList: MutableLiveData<Resource<List<BreedInfoResponse>>> = MutableLiveData()

    private fun getBreedsList(): LiveData<Resource<List<BreedInfoResponse>>> {
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

    fun readData() = getBreedsList()
}