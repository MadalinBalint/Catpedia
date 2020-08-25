package com.mendelin.catpedia.data_access_layer.networking.models.responses

import androidx.annotation.Keep

@Keep
class BreedImageResponse {
    val breeds: List<BreedInfoResponse>? = null
    val id: String? = null
    val url: String? = null
    val width: Int? = null
    val height: Int? = null
}