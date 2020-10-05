package com.mendelin.catpedia.breeds_list.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mendelin.catpedia.repositories.BreedInfoRepository
import com.mendelin.catpedia.models.BreedInfoResponse
import com.mendelin.catpedia.repositories.CatBreedsRepository
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class BreedsViewModel @Inject constructor(
    private val breedsRepository: CatBreedsRepository,
    private val breedInfoRepository: BreedInfoRepository
) : ViewModel() {
    private val originalBreedList: ArrayList<BreedInfoResponse> = arrayListOf()

    private val breedsList = MutableLiveData<ArrayList<BreedInfoResponse>>()

    private val errorFilter = MutableLiveData<String>()

    fun getOriginalBreedList(): ArrayList<BreedInfoResponse> = originalBreedList
    fun setOriginalBreedList(list: List<BreedInfoResponse>) {
        originalBreedList.apply {
            clear()
            addAll(list)
        }

        breedsList.postValue(originalBreedList)
    }

    fun getBreedsList() = breedsList
    fun getErrorFilter() = errorFilter

    fun filter(query: String?) {
        query?.let {
            if (query.isNotEmpty()) {
                val filteredList = originalBreedList.filter {
                    it.origin?.toLowerCase(Locale.ROOT) == query.toLowerCase(Locale.ROOT) ||
                            it.origin?.toLowerCase(Locale.ROOT)?.indexOf(query.toLowerCase(Locale.ROOT))!! >= 0
                }

                if (filteredList.isEmpty()) {
                    errorFilter.postValue("The country '${query}' for the cat's origin doesn't exist in our list.")
                } else
                    breedsList.postValue(ArrayList(filteredList))
            } else
                breedsList.postValue(originalBreedList)
        }
    }

    fun readBreedsData() = breedsRepository.readData()

    fun readBreedInfoData(breedId: String) = breedInfoRepository.readData(breedId)
}