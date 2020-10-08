package com.mendelin.catpedia.repository

import android.content.Context
import com.mendelin.catpedia.BuildConfig
import com.mendelin.catpedia.repository.local.JsonStorage
import com.mendelin.catpedia.models.LoginResponse
import javax.inject.Inject

class MockedLoginRepository @Inject constructor(private val storage: JsonStorage) {

    fun getLoginRespone(context: Context, username: String, password: String): LoginResponse {
        if (username == BuildConfig.MOCKED_USER_NAME && password == BuildConfig.MOCKED_USER_PASSWORD) {
            storage.getSuccessfulLoginData(context)?.let {
                return it
            }
        }

        throw Exception(storage.getFailedLoginData(context)?.message)
    }
}