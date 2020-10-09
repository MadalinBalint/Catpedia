package com.mendelin.catpedia.repository

import android.content.Context
import com.mendelin.catpedia.repository.remote.CatpediaRemoteApiService
import javax.inject.Inject

class CatpediaRepository @Inject constructor(private val remoteService: CatpediaRemoteApiService) {

    fun getCatBreeds() = remoteService.getCatBreeds()

    fun mockedUserLogin(context: Context, name: String, password: String) =
        remoteService.mockedUserLogin(context, name, password)
}