package com.mendelin.catpedia.common

import android.content.Context
import com.mendelin.catpedia.R
import com.mendelin.catpedia.presentation_layer.custom_views.AlertBox
import java.io.ByteArrayOutputStream
import java.io.File
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
}