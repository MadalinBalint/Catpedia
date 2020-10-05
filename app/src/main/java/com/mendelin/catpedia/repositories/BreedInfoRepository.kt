package com.mendelin.catpedia.repositories

import com.mendelin.catpedia.retrofit.CatpediaApiNetworkCall
import javax.inject.Inject

class BreedInfoRepository @Inject constructor(
    private val networkCall: CatpediaApiNetworkCall
) {
    fun readData(breedId: String) = networkCall.getBreedImage(breedId)
}