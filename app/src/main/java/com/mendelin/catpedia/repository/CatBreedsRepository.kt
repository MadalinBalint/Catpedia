package com.mendelin.catpedia.repository

import com.mendelin.catpedia.repository.remote.CatpediaRemoteApiService
import javax.inject.Inject

class CatBreedsRepository @Inject constructor(private val remoteService: CatpediaRemoteApiService) {

    fun getCatBreeds() = remoteService.getCatBreeds()
}