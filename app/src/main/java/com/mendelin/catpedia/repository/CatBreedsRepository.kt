package com.mendelin.catpedia.repository

import com.mendelin.catpedia.networking.CatpediaApiNetworkCall
import javax.inject.Inject

class CatBreedsRepository @Inject constructor(
    private val networkCall: CatpediaApiNetworkCall
) {
    fun readData() = networkCall.getBreedsList()
}