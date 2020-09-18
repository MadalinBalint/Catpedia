package com.mendelin.catpedia.welcome_screen.bussiness_logic.repository

import com.mendelin.catpedia.R
import com.mendelin.catpedia.repository.LocalRepository
import com.mendelin.catpedia.welcome_screen.bussiness_logic.models.LoginResponse
import javax.inject.Inject

class LoginMockupRepository @Inject constructor() {
    val dataLoginOk = LocalRepository(R.raw.login_ok, LoginResponse::class.java)
    val dataLoginWrong = LocalRepository(R.raw.login_wrong, LoginResponse::class.java)
}