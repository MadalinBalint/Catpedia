package com.mendelin.catpedia

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.mendelin.catpedia.common.ResourceUtils

@BindingAdapter("imageUrl")
/* Binding adapter for the cat image in breeds list */
fun bindImage(imgView: ImageView, imgUrl: String?) {
    ResourceUtils.showImage(imgView, imgUrl)
}