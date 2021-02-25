package com.mendelin.catpedia.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.mendelin.catpedia.R

@BindingAdapter("imageUrl")
/* Binding adapter for the cat image in breeds list */
fun bindImage(imgView: ImageView, imageUrl: String?) {
    imageUrl?.let {
        val circularProgressDrawable = CircularProgressDrawable(imgView.context)
        circularProgressDrawable.strokeWidth = 6f
        circularProgressDrawable.centerRadius = 50f
        circularProgressDrawable.start()

        Glide.with(imgView.context)
            .applyDefaultRequestOptions(
                RequestOptions()
                    .format(DecodeFormat.PREFER_RGB_565)
                    .disallowHardwareConfig()
            )
            .load(imageUrl)
            .optionalCenterCrop()
            .placeholder(circularProgressDrawable)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .error(R.drawable.ic_logo)
            .into(imgView)
    }
}