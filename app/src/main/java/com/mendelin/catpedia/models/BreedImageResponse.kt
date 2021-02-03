package com.mendelin.catpedia.models

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Parcelize
@Keep
data class BreedImageResponse(
    val breeds: List<BaseBreedInfoResponse>,
    val id: String,
    val width: Int,
    val height: Int,
    val url: String
) : Parcelable