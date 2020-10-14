package com.mendelin.catpedia.repository.local

import android.content.Context
import com.mendelin.catpedia.models.LoginResponse
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val response: MockedLoginResponse
) {
    fun mockedUserLogin(context: Context, name: String, password: String): Single<LoginResponse> {
        return try {
            val data = response.getLoginRespone(context, name, password)
            Single.just(data)
        } catch (e: Exception) {
            Single.error(Throwable(e.message))
        }
    }
}