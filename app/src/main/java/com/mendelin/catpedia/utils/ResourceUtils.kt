package com.mendelin.catpedia.utils

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.mendelin.catpedia.R
import com.mendelin.catpedia.custom_views.AlertBox
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

object ResourceUtils {
    fun readTextFile(inputStream: InputStream): String {
        val outputStream = ByteArrayOutputStream()

        val buf = ByteArray(4096)
        var len = 0
        try {
            while ({ len = inputStream.read(buf); len }() != -1) {
                outputStream.write(buf, 0, len)
            }
            outputStream.close()
            inputStream.close()
        } catch (e: IOException) {
        }

        return outputStream.toString()
    }

    fun showErrorAlert(context: Context, msg: String) {
        val alert = AlertBox()

        alert.setPositiveButtonListener { dialog, _ ->
            dialog.dismiss()
        }

        alert.showAlert(context,
            context.getString(R.string.alert_error),
            msg,
            context.getString(R.string.alert_ok),
            null
        )
    }

    fun showImage(imgView: ImageView, imageUrl: String?) {
        imageUrl?.let {
            val circularProgressDrawable = CircularProgressDrawable(imgView.context)
            circularProgressDrawable.strokeWidth = 6f
            circularProgressDrawable.centerRadius = 50f
            circularProgressDrawable.start()

            /* Load cat image */
            Glide.with(imgView.context)
                .applyDefaultRequestOptions(
                    RequestOptions()
                        .format(DecodeFormat.PREFER_RGB_565)
                        .disallowHardwareConfig())
                .load(imageUrl)
                .optionalCenterCrop()
                .placeholder(circularProgressDrawable)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgView)
        }
    }
}