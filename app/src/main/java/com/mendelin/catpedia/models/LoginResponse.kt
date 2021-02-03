package com.mendelin.catpedia.models

import androidx.annotation.Keep

@Keep
data class LoginResponse(
    val status: Boolean? = null,
    val message: String? = null,
    var data: UserObject? = null
)