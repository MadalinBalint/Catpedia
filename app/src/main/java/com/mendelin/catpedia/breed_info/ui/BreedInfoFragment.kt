package com.mendelin.catpedia.breed_info.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mendelin.catpedia.R
import com.mendelin.catpedia.utils.ResourceUtils
import com.mendelin.catpedia.base_classes.BaseFragment
import kotlinx.android.synthetic.main.fragment_breed_info.*

class BreedInfoFragment : BaseFragment(R.layout.fragment_breed_info) {

    private val args: BreedInfoFragmentArgs by navArgs()

    override fun onResume() {
        super.onResume()

        toolbarOff()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ResourceUtils.showImage(imgCat, args.imageUrl)

        /* Setup the rest of the data */
        txtBreedName.text = args.name
        txtBreedDescription.text = args.description
        txtBreedCountry.text = args.country
        txtBreedTemperament.text = args.temperament

        if (args.link.isNotEmpty()) {
            txtBreedWikipediaLink.text = args.link
        } else {
            titleBreedWikipediaLink.visibility = View.GONE
            txtBreedWikipediaLink.visibility = View.GONE
        }

        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}