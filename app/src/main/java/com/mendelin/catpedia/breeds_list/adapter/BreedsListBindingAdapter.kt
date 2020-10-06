package com.mendelin.catpedia.breeds_list.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.mendelin.catpedia.utils.UIHelper

@BindingAdapter("imageUrl")
/* Binding adapter for the cat image in breeds list */
fun bindImage(imgView: ImageView, imgUrl: String?) {
    UIHelper.showImage(imgView, imgUrl)
}