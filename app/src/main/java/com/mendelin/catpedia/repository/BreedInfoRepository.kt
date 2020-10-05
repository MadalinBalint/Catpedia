package com.mendelin.catpedia.repository

import com.mendelin.catpedia.networking.CatpediaApiNetworkCall
import javax.inject.Inject

class BreedInfoRepository @Inject constructor(
    private val networkCall: CatpediaApiNetworkCall
) {
    fun readData(breedId: String) = networkCall.getBreedImage(breedId)
}