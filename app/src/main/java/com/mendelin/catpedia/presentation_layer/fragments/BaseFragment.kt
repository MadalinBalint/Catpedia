/*
 *
 * BaseFragment.kt on ShopMyCloset
 *
 * Created by Madalin Balint on 31/7/2019 8:42.
 * Copyright (c) 2019 Kodiak Mobile SRL. All rights reserved.
 */

package com.mendelin.catpedia.presentation_layer.fragments

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mendelin.catpedia.R
import com.mendelin.catpedia.presentation_layer.custom_views.AlertBox


abstract class BaseFragment(private val layoutId: Int) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }

    fun hideSoftKeyboard() {
        val inputManager = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?

        if (view != null && inputManager != null) {
            inputManager.hideSoftInputFromWindow(requireView().windowToken, 0)
            inputManager.hideSoftInputFromInputMethod(requireView().windowToken, 0)
        }
    }

    /* Toolbar config settings */
    private fun logoToolbarVisibility(state: Boolean) {
        val logo = requireActivity().findViewById<ImageView>(R.id.imgLogo)
        logo.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun backButtonVisibility(state: Boolean) {
        val toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbar)

        if (state)
            toolbar.setNavigationIcon(R.drawable.ic_back_button)
        else
            toolbar.navigationIcon = null
    }

    private fun setToolbarTitle(title: String = "") {
        val toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = title
    }

    fun getNavigationResult(key: String = "result") =
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(key)

    fun setNavigationResult(result: String, key: String = "result") {
        findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
    }

    fun toolbarOff() {
        val toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbar)
        toolbar.visibility = View.GONE
    }

    fun toolbarOn() {
        val toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbar)
        toolbar.visibility = View.VISIBLE
    }

    fun showErrorAlert(context: Context, msg: String) {
        val alert = AlertBox(context)

        alert.setPositiveButtonListener(DialogInterface.OnClickListener { dialog, _ ->
            dialog.dismiss()
        })

        alert.showAlert(
            getString(R.string.alert_error),
            msg,
            getString(R.string.alert_ok),
            null
        )
    }
}