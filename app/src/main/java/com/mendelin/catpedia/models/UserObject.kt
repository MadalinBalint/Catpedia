package com.mendelin.catpedia.models

import androidx.annotation.Keep
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class UserObject(
    val user_name: String? = null,
    val user_email: String? = null,
    val access_token: String? = null,
)