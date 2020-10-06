package com.mendelin.catpedia.repository

import com.mendelin.catpedia.networking.CatpediaApiProvider
import javax.inject.Inject

class BreedInfoRepository @Inject constructor(
    private val provider: CatpediaApiProvider
) {
    fun readData(breedId: String) = provider.getBreedImage(breedId)
}