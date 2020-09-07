package com.mendelin.catpedia.welcome_screen.bussiness_logic.models

import androidx.annotation.Keep
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class LoginResponse(
    val status: Boolean? = null,
    val message: String? = null,
    var data: UserObject? = null
)