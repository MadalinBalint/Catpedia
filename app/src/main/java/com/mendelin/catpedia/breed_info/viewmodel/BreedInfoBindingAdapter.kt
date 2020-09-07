package com.mendelin.catpedia.breed_info.viewmodel

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.mendelin.catpedia.utils.ResourceUtils

@BindingAdapter("catImage")
/* Binding adapter for the cat image in breeds list */
fun bindImage(imgView: ImageView, imgUrl: String?) {
    ResourceUtils.showImage(imgView, imgUrl)
}