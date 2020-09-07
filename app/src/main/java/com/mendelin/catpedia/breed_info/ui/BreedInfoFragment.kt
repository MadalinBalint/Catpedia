package com.mendelin.catpedia.breed_info.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mendelin.catpedia.BreedInfoBinding
import com.mendelin.catpedia.R
import com.mendelin.catpedia.base_classes.BaseFragment
import kotlinx.android.synthetic.main.fragment_breed_info.*

class BreedInfoFragment : BaseFragment(R.layout.fragment_breed_info) {

    private val args: BreedInfoFragmentArgs by navArgs()

    lateinit var binding: BreedInfoBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = BreedInfoBinding.inflate(inflater, container, false)
        binding.args = args
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        toolbarOff()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}