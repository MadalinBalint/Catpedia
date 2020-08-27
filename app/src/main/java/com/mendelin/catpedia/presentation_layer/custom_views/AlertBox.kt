package com.mendelin.catpedia.presentation_layer.custom_views

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class AlertBox : AppCompatActivity() {
    private var listenerPositiveButton: DialogInterface.OnClickListener? = null
    private var listenerNegativeButton: DialogInterface.OnClickListener? = null

    fun setPositiveButtonListener(listener: DialogInterface.OnClickListener) {
        listenerPositiveButton = listener
    }

    fun setNegativeButtonListener(listener: DialogInterface.OnClickListener) {
        listenerNegativeButton = listener
    }

    fun showAlert(context: Context, title: String, message: String, btnPositive: String?, btnNegative: String?) {
        val alertDialogBuilder = AlertDialog.Builder(context)

        alertDialogBuilder.setTitle(title)
        alertDialogBuilder.setMessage(message)

        if (listenerPositiveButton != null)
            alertDialogBuilder.setPositiveButton(btnPositive, listenerPositiveButton)

        if (listenerNegativeButton != null)
            alertDialogBuilder.setNegativeButton(btnNegative, listenerNegativeButton)

        val alertDialog = alertDialogBuilder.create()
        if (!alertDialog.isShowing) {
            alertDialog.show()
        }
    }
}