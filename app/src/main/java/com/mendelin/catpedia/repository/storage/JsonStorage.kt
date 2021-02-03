package com.mendelin.catpedia.repository.storage

import android.content.Context
import com.google.gson.Gson
import com.mendelin.catpedia.R
import com.mendelin.catpedia.models.LoginResponse
import javax.inject.Inject

class JsonStorage @Inject constructor(val gson: Gson) {
    fun getSuccessfulLoginData(context: Context) =
        BaseLocalStorage(gson, R.raw.login_success, LoginResponse::class.java).readData(context)

    fun getFailedLoginData(context: Context) =
        BaseLocalStorage(gson, R.raw.login_failed, LoginResponse::class.java).readData(context)
}