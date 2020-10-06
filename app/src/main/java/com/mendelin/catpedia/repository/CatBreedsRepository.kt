package com.mendelin.catpedia.repository

import com.mendelin.catpedia.networking.CatpediaApiProvider
import javax.inject.Inject

class CatBreedsRepository @Inject constructor(
    private val provider: CatpediaApiProvider
) {
    fun readData() = provider.getBreedsList()
}