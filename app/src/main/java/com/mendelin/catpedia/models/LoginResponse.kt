package com.mendelin.catpedia.models

import androidx.annotation.Keep
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class LoginResponse(
    val status: Boolean? = null,
    val message: String? = null,
    var data: UserObject? = null
)