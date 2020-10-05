package com.mendelin.catpedia.repositories

import com.mendelin.catpedia.R
import com.mendelin.catpedia.models.LoginResponse
import com.squareup.moshi.Moshi
import javax.inject.Inject

class LoginMockupRepository @Inject constructor(val moshi: Moshi) {
    val dataLoginOk = LocalRepository(moshi, R.raw.login_ok, LoginResponse::class.java)
    val dataLoginWrong = LocalRepository(moshi, R.raw.login_wrong, LoginResponse::class.java)
}