package com.mendelin.catpedia.data_access_layer.networking.models.responses

import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@Keep
@JsonClass(generateAdapter = true)
data class BreedImageResponse(
    val id: String,
    val url: String,
    val width: Int,
    val height: Int
) : Parcelable {
}