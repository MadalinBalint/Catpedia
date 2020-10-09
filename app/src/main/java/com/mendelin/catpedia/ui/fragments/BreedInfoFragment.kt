package com.mendelin.catpedia.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mendelin.catpedia.BreedInfoBinding
import com.mendelin.catpedia.R
import com.mendelin.catpedia.ui.activity.ActivityCallback
import dagger.android.support.DaggerFragment

class BreedInfoFragment : DaggerFragment(R.layout.fragment_breed_info) {

    private val args: BreedInfoFragmentArgs by navArgs()

    lateinit var binding: BreedInfoBinding
    private var activityCallback: ActivityCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ActivityCallback) {
            activityCallback = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = BreedInfoBinding.inflate(inflater, container, false)
        binding.args = args
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        activityCallback?.showToolbar(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}