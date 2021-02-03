package com.mendelin.catpedia.models

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Parcelize
@Keep
data class BaseBreedImageResponse(
    val id: String,
    val width: Int,
    val height: Int,
    val url: String
) : Parcelable