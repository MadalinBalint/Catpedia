package com.mendelin.catpedia.presentation_layer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.mendelin.catpedia.R


abstract class BaseFragment(private val layoutId: Int) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }

    fun toolbarOff() {
        val toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbar)
        toolbar.visibility = View.GONE
    }

    fun toolbarOn() {
        val toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbar)
        toolbar.visibility = View.VISIBLE
    }
}