package com.mendelin.catpedia.models

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class BreedImageResponse(
    val breeds: List<BaseBreedInfoResponse>,
    val id: String,
    val width: Int,
    val height: Int,
    val url: String
) : Parcelable