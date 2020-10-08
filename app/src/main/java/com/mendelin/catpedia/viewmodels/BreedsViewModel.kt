package com.mendelin.catpedia.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mendelin.catpedia.models.BreedInfoResponse
import com.mendelin.catpedia.repository.CatBreedsRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class BreedsViewModel @Inject constructor(
    private val breedsRepository: CatBreedsRepository
) : ViewModel() {
    private val originalBreedList: ArrayList<BreedInfoResponse> = arrayListOf()
    private val breedsList = MutableLiveData<ArrayList<BreedInfoResponse>>()
    private val error = MutableLiveData<String>()
    private val isLoading = MutableLiveData(false)
    private val disposables = CompositeDisposable()

    fun fetchBreeds() {
        isLoading.value = true
        disposables.add(
            breedsRepository.getCatBreeds()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { list ->
                        isLoading.value = false
                        setOriginalBreedList(list)
                    },
                    { throwable ->
                        isLoading.value = false
                        error.value = throwable.message
                    }
                )
        )
    }

    private fun setOriginalBreedList(list: List<BreedInfoResponse>) {
        originalBreedList.apply {
            clear()
            addAll(list)
        }

        breedsList.postValue(originalBreedList)
    }

    fun getBreedsList(): LiveData<ArrayList<BreedInfoResponse>> = breedsList
    fun getErrorFilter(): LiveData<String> = error
    fun getLoadingObservable(): LiveData<Boolean> = isLoading

    fun filter(query: String?) {
        query?.let {
            if (query.isNotEmpty()) {
                val filteredList = originalBreedList.filter {
                    it.origin?.toLowerCase(Locale.ROOT) == query.toLowerCase(Locale.ROOT) ||
                            it.origin?.toLowerCase(Locale.ROOT)
                                ?.indexOf(query.toLowerCase(Locale.ROOT))!! >= 0
                }

                if (filteredList.isEmpty()) {
                    error.postValue("The country '${query}' for the cat's origin doesn't exist in our list.")
                } else
                    breedsList.postValue(ArrayList(filteredList))
            } else
                breedsList.postValue(originalBreedList)
        }
    }

    fun onErrorHandled() {
        error.value = ""
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}