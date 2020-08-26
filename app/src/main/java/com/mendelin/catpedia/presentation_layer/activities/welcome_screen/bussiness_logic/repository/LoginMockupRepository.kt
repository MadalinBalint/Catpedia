package com.mendelin.catpedia.presentation_layer.activities.welcome_screen.bussiness_logic.repository

import com.mendelin.catpedia.R
import com.mendelin.catpedia.data_access_layer.repository.LocalRepository
import com.mendelin.catpedia.presentation_layer.activities.welcome_screen.bussiness_logic.models.LoginResponse

object LoginMockupRepository {
    val dataLoginOk = LocalRepository(R.raw.login_ok, LoginResponse::class.java).readData()
    val dataLoginWrong = LocalRepository(R.raw.login_wrong, LoginResponse::class.java).readData()
}