package com.mendelin.catpedia.repository.local

import android.content.Context
import com.mendelin.catpedia.R
import com.mendelin.catpedia.models.LoginResponse
import com.squareup.moshi.Moshi
import javax.inject.Inject

class JsonStorage @Inject constructor(val moshi: Moshi) {
    fun getSuccessfulLoginData(context: Context) =
        BaseLocalStorage(moshi, R.raw.login_success, LoginResponse::class.java).readData(context)

    fun getFailedLoginData(context: Context) =
        BaseLocalStorage(moshi, R.raw.login_failed, LoginResponse::class.java).readData(context)
}