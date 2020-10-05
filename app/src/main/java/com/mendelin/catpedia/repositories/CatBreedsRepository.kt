package com.mendelin.catpedia.repositories

import com.mendelin.catpedia.retrofit.CatpediaApiNetworkCall
import javax.inject.Inject

class CatBreedsRepository @Inject constructor(
    private val networkCall: CatpediaApiNetworkCall
) {
    fun readData() = networkCall.getBreedsList()
}