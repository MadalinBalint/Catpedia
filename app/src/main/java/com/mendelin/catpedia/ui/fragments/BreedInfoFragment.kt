package com.mendelin.catpedia.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mendelin.catpedia.BreedInfoDataBinding
import com.mendelin.catpedia.R
import com.mendelin.catpedia.ui.BreedInfoFragmentArgs
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_breed_info.*

class BreedInfoFragment : DaggerFragment(R.layout.fragment_breed_info) {

    private val args: BreedInfoFragmentArgs by navArgs()

    lateinit var binding: BreedInfoDataBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = BreedInfoDataBinding.inflate(inflater, container, false)
        binding.args = args
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        val toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbar)
        toolbar.visibility = View.GONE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}