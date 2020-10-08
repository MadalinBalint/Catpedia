package com.mendelin.catpedia.repository

import android.content.Context
import com.mendelin.catpedia.repository.remote.CatpediaRemoteApiService
import com.mendelin.catpedia.repository.remote.CatpediaRemoteApiServiceMocked
import javax.inject.Inject

class MockedRepository @Inject constructor(private val service: CatpediaRemoteApiServiceMocked) {

    fun mockedUserLogin(context: Context, name: String, password: String) =
        service.mockedUserLogin(context, name, password)
}